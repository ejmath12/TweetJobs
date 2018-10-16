import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter.TwitterUtils
import twitter4j.auth.OAuthAuthorization
import twitter4j.conf.ConfigurationBuilder

object DriverMain {
  def main(args: Array[String]): Unit = {
    val ss:SparkSession = SparkSession.builder.
      appName(Constants.appName).
      master(Constants.accessType).
      enableHiveSupport.
      getOrCreate()
    import ss.implicits._
    val hashtagsDS = ss.read.textFile(Constants.inputFile).as[String].flatMap(s => s.split(","))
    val conf = ss.sparkContext
    val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = Array(System.getenv(Constants.consumerKey),
      System.getenv(Constants.consumerSecretKey),
      System.getenv(Constants.accessToken),
      System.getenv(Constants.accessTokenSecret))
    val cb = new ConfigurationBuilder
    val ssc = new StreamingContext(conf, Seconds(5))
    cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)
    val auth = new OAuthAuthorization(cb.build)
    val hashtags = hashtagsDS.collect()
    val producerConf = KafkaWrapper.producerConfig
    val kafkaProd = conf.broadcast(KafkaWrapper(producerConf))
//    for(i <- 1 to 10) {
//      kafkaProd.value.send(Constants.topicName, hashtags(i))
//      Thread.sleep(5000)
//    }
    val tweets = TwitterUtils.createStream(ssc, Some(auth), List.empty[String])
    tweets.foreachRDD( rdd => {
      val save = rdd.filter(status =>
          status.getLang.equals("en")
        ).filter(engTweetsWithHash => List(engTweetsWithHash.getText.split(" "), hashtags).reduce((a,b) => a intersect b).size>0)
        .map(engTweetsWithHashFiltered =>
          (List(engTweetsWithHashFiltered.getText.split(" "), hashtags)
            .reduce((a,b) => a intersect b).mkString(","),engTweetsWithHashFiltered.getText))

      save.persist()
      save.map(s => kafkaProd.value.send(Constants.topicName,s._1 + Constants.splitter + s._2));
      //need to change to hbase/cassandra
      save.toDF(Constants.hashtags,Constants.tweet).write.mode(Constants.append).saveAsTable(Constants.writeTable)
    })

    ssc.start()
    ssc.awaitTermination()
  }
}

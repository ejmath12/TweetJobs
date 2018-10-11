import java.util.Properties

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.clients.producer.KafkaProducer

class KafkaWrapper(createProducer: () => KafkaProducer[String, String]) extends Serializable {

  lazy val producer = createProducer()

  def send(topic: String, value: String): Unit = producer.send(new ProducerRecord(topic, value))
}

object KafkaWrapper {
  def apply(config: Properties): KafkaWrapper = {
    val f = () => {
      val producer = new KafkaProducer[String,String](config)
      sys.addShutdownHook {
        producer.close()
      }

      producer
    }
    new KafkaWrapper(f)
  }
  val producerConfig = new Properties()
  producerConfig.put("bootstrap.servers" , "localhost:9092")
  producerConfig.put("key.serializer" , classOf[StringSerializer].getName)
  producerConfig.put("value.serializer" ,classOf[StringSerializer].getName)

}
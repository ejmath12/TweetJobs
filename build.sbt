name := "TwitterAnalysis"

version := "0.1"

scalaVersion := "2.11.8"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.1"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.1"
// https://mvnrepository.com/artifact/org.apache.hive/hive-jdbc
libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.2.1"
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-twitter
// https://mvnrepository.com/artifact/org.apache.bahir/spark-streaming-twitter
libraryDependencies += "org.apache.bahir" %% "spark-streaming-twitter" % "2.2.1"
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.2.1"
//// https://mvnrepository.com/artifact/org.apache.hbase/hbase-server
//libraryDependencies += "org.apache.hbase" % "hbase-server" % "2.1.0"
//// https://mvnrepository.com/artifact/org.apache.hbase/hbase-client
//libraryDependencies += "org.apache.hbase" % "hbase-client" % "2.1.0"
//// https://mvnrepository.com/artifact/org.apache.hbase/hbase-common
//libraryDependencies += "org.apache.hbase" % "hbase-common" % "2.1.0"
// https://mvnrepository.com/artifact/org.apache.hbase/hbase
libraryDependencies += "org.apache.hbase" % "hbase" % "0.94.15"
// https://mvnrepository.com/artifact/org.apache.kafka/kafka
libraryDependencies += "org.apache.kafka" %% "kafka" % "0.10.2.2"
// https://mvnrepository.com/artifact/com.datastax.spark/spark-cassandra-connector
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.3.2"

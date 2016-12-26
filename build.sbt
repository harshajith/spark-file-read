name := """spark-search"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.4",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.4",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
    "org.apache.spark" % "spark-core_2.11" % "1.6.0",
  "org.apache.spark" % "spark-sql_2.11" % "1.6.0",
  "com.databricks" % "spark-csv_2.11" % "1.4.0"

)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

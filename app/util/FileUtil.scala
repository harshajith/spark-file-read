package util

import com.google.inject.Singleton
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by Harsha on 27/7/16.
  */
@Singleton
object FileUtil{

  val sc = new SparkContext(new SparkConf().setAppName("track_people").setMaster("local[4]").set("spark.driver.allowMultipleContexts", "true"))
  val sqlContext = new SQLContext(sc)
  val df = sqlContext.read
    .format("com.databricks.spark.csv")
    .option("header", "true") // Use first line of all files as header
    .option("inferSchema", "true") // Automatically infer data types
    .load("reduced.csv")

  val df_connected = sqlContext.read
    .format("com.databricks.spark.csv")
    .option("header", "true") // Use first line of all files as header
    .option("inferSchema", "true") // Automatically infer data types
    .load("edges.csv")


  def getDataFrameForRawCSV : DataFrame = {
    df
  }

  def getDataframeFromConnectedPeople : DataFrame = {
    df_connected
  }
}

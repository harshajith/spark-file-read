package services.impl

import java.util.Date

import com.google.inject.{Inject, Singleton}
import org.slf4j.Logger
import services.SearchService
import util.{LoggerUtil, FileUtil, LoggerHelper}

/**
  * Created by Harsha on 27/7/16.
  */
@Singleton
class SearchServiceImpl @Inject() () extends SearchService with LoggerUtil{
  override val log: Logger = LoggerHelper[SearchServiceImpl]

  override def searchFromRowFile(uid1: String, uid2: String): Boolean = {
    val df = FileUtil.getDataFrameForRawCSV
    val df1 = df.filter(df("uid").equalTo(uid1))
    val df2 = df.filter(df("uid").equalTo(uid2))
    val rows = df1.join(df2, Seq("timestamp", "x", "y", "floor"), "inner").collect()
    log.debug("size" + rows.size)
    if(rows.size > 0) {
      log.debug("have met")
      log.debug( "Details of the meetup" + rows(0).toString)
      log.debug("end time : " + new Date())
      true
    }else {
      log.debug("haven't met")
      log.debug("end time : " + new Date())
      false
    }
  }

  override def searchFromConnectedPool(uid: String, uid1: String) : Boolean = {
    val df_connected = FileUtil.getDataframeFromConnectedPeople
    log.debug("start time : " + new Date())
    val rows = df_connected.filter(df_connected("uid") === uid && df_connected("uid1") === uid1).collect()
    log.debug("size" + rows.size)
    if(rows.size > 0) {
      log.debug( " : data : " + rows(0).toString)
      log.debug("have met")
      log.debug("end time : " + new Date())
      true
    }else {
      log.debug("haven't met")
      log.debug("end time : " + new Date())
      false
    }
  }

  override def createConnectedPeople(): Unit = {
    val df = FileUtil.getDataFrameForRawCSV
    val df2 = df.withColumnRenamed("uid", "uid1")
    val df3 = df.join(df2, (df("timestamp") === df2("timestamp")) && (df("x") === df2("x")) && (df("y") === df2("y")) && (df("floor") === df2("floor")) && (df("uid") !== df2("uid1")), "inner")
    df3.select("uid", "uid1").coalesce(2 ).write
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .save("edges.csv")
  }


}

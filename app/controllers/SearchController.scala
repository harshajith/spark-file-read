package controllers

import java.util.Date

import com.google.inject.{Singleton, Inject}
import org.slf4j.Logger
import play.api.mvc.{Action, Controller}
import services.SearchService
import util.{LoggerUtil, LoggerHelper}

/**
  * Created by Harsha on 23/7/16.
  */
@Singleton
class SearchController @Inject() (seachService:SearchService) extends Controller with LoggerUtil{

  override val log: Logger = LoggerHelper[SearchController]

  def index = Action {
    Ok("app is up and running")
  }

  /**
    * Search from the raw csv file if the given people UID has met or seen
    * @param uid1
    * @param uid2
    * @return
    */
  def searchVersion1(uid1: String, uid2: String)  = Action {
    log.debug("start time : " + new Date())
    try{
      if(uid1 != null && uid2 != null && !uid1.isEmpty && !uid2.isEmpty){
        seachService.searchFromRowFile(uid1,uid2) match {
          case true => log.debug("end time : " + new Date())
            Ok("met")
          case false => log.debug("end time : " + new Date())
            Ok("not met")
        }
      }else {
        BadRequest
      }
    }catch {
      case e:Exception => {
        log.error("Exception occurred while created connected people, {}", e.toString)
        InternalServerError
      }
    }
  }


  /**
    * Search from the list of already connected people,
    * Connected means they have been at the same place at the same time
    * @param uid1
    * @param uid2
    * @return
    */
  def searchVersion2(uid1: String, uid2: String) = Action {
    log.debug("start time : " + new Date())
    try{
      if(uid1 != null && uid2 != null && !uid1.isEmpty && !uid2.isEmpty){
        seachService.searchFromConnectedPool(uid1,uid2) match {
          case true => log.debug("end time : " + new Date())
            Ok("met")
          case false => log.debug("end time : " + new Date())
            Ok("not met")
        }
      }else {
        BadRequest
      }
    }catch {
      case e:Exception => {
        log.error("Exception occurred while created connected people, {}", e.toString)
        InternalServerError
      }
    }
  }


  /**
    * Create a csv file from the people who have been in the same place at the same time
    * @return
    */
  def createEdges = Action {
    try{
      seachService.createConnectedPeople()
      Ok("created connected people file - edges.csv")
    }catch {
      case e:Exception => {
        log.error("Exception occurred while created connected people, {}", e.toString)
        InternalServerError
      }
    }

  }


}

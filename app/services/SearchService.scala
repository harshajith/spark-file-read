package services

/**
  * Created by Harsha on 27/7/16.
  */
trait SearchService {

  def searchFromRowFile(uid1:String, uid2:String) : Boolean
  def searchFromConnectedPool(uid1:String, uid2:String) : Boolean
  def createConnectedPeople()

}

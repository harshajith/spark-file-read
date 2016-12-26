package util

import org.slf4j.{LoggerFactory, Logger}

import scala.reflect.ClassTag

/**
  * Created by Harsha on 3/4/16.
  */
trait LoggerUtil {
  val log:Logger
}

object LoggerHelper {
  def apply[T](implicit  cls:ClassTag[T]) = LoggerFactory.getLogger(cls.runtimeClass)
}
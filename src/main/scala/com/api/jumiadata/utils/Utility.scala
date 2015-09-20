package com.api.jumiadata.utils

import collection.JavaConversions._
import scala.collection.immutable.Map

/**
 * @author olalekanelesin
 */
object Utility {
  
  def toScalaMap(javaMap: java.util.Map[String, Object]): Map[String, String] = {
    return javaMap.toMap.asInstanceOf[Map[String, String]]
  }
}
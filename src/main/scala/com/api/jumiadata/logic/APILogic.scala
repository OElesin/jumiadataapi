package com.api.jumiadata.logic

import com.api.jumiadata.dao.APIDAO
import com.api.jumiadata.utils.Utility
import com.sksamuel.elastic4s.ElasticDsl._
import scala.concurrent._
import ExecutionContext.Implicits.global
import com.sksamuel.elastic4s.QueryDefinition
import scala.util.{Success, Failure}
import com.api.jumiadata.utils.APIresponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import java.io.StringWriter
import scala.collection.mutable.ArrayBuffer
import com.sksamuel.elastic4s.FuzzyQueryDefinition


/**
 
* @author olalekanelesin
 */
class APILogic extends APIDAO{
   val mapper = new ObjectMapper()
   mapper.registerModule(DefaultScalaModule)
   val out = new StringWriter
  
  def search_product(){
    
  }
  
  def get_product(id: String): String ={
    val product_data = this.get_doc(id)
    val payload = Utility.toScalaMap(product_data.getSource)
    return APIresponse.successResponese(payload, "Product Found")
  }
  
  def get_all_products(): String ={
    var temp = new String
    var arrayData: ArrayBuffer[Any] = new ArrayBuffer 
    val conditions: Array[QueryDefinition] = Array(matchall)

      temp = APIresponse.errorResponse("You have exceeded your search limit", 500)

      val payload = this.search_documents(conditions, 1, 80)
      payload.getHits.getHits.foreach { data => 
       arrayData += Utility.toScalaMap(data.getSource)   
      }
      temp = APIresponse.bulkResponse(arrayData, "Data found for ")
    return temp
  }
  
  def get_brand_products(brand_name: String) : String ={
    var temp = new String
    var arrayData: ArrayBuffer[Any] = new ArrayBuffer
    var start = 0
    var limit = 500
    val conditions: Array[QueryDefinition] = Array(matches("Brand", brand_name))
    if(limit - start > 700){
      temp = APIresponse.errorResponse("You have exceeded your search limit", 500)
    }else{
      val payload = this.search_documents(conditions, start, limit)
      payload.getHits.getHits.foreach { data => 
       arrayData += Utility.toScalaMap(data.getSource)   
      }
      temp = APIresponse.bulkResponse(arrayData, s"Data found for $brand_name.")
    }
    return temp
  }
  
  def categories(category_name: String, start: Int=0, limit: Int=10) : String ={
    var temp = new String
    var arrayData: ArrayBuffer[Any] = new ArrayBuffer 
    val conditions: Array[QueryDefinition] = Array(matches("Brand", category_name))
    if(limit - start > 60){
      temp = APIresponse.errorResponse("You have exceeded your search limit", 500)
    }else{
      val payload = this.search_documents(conditions, start, limit)
      payload.getHits.getHits.foreach { data => 
       arrayData += Utility.toScalaMap(data.getSource)   
      }
      temp = APIresponse.bulkResponse(arrayData, s"Data found for $category_name.")
    }
    return temp
  }
  
  def product_search(some_name: String) : String ={
    var arrayData: ArrayBuffer[Any] = new ArrayBuffer    
    val conditions = fuzzyQuery("Product_name", some_name).maxExpansions(5)
    val payload = this.fuzzy_search(conditions)
      payload.getHits.getHits.foreach { data => 
       arrayData += Utility.toScalaMap(data.getSource)   
      }
    return APIresponse.bulkResponse(arrayData, s"Suggestions found for $some_name.")
  }
  
  
}
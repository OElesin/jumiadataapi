package com.api.jumiadata.utils

import org.elasticsearch.action.delete.DeleteResponse
import scala.concurrent.Future
import com.sksamuel.elastic4s.mappings.FieldType._
import org.elasticsearch.action.update.UpdateResponse
import org.elasticsearch.action.index.IndexResponse
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.QueryDefinition
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.common.settings.ImmutableSettings
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.FuzzyQueryDefinition

/**
 * @author olalekanelesin
 */

object Elastic  {
  
  val uri = ElasticsearchClientUri("elasticsearch://159.203.76.168:9300")
  val settings = ImmutableSettings.settingsBuilder().put("cluster.name", "ELESIN_ELASTIC_DATACENTRE").build()
  val client = ElasticClient.remote(settings, uri)
    val elasticIndex = "products"
    val elasticType = "product"
  
  def add_mappings(params: Map[String, Object]) {
    
  }
  
  def create_index(params: Map[String, Any]): CreateIndexResponse = {
    return client.execute {
      create index elasticIndex mappings (
        elasticType as (
          params.keys.toString typed StringType
        )
      )
    }.await
  }
  
  def get_document(docid: String) : GetResponse ={
    val data = client.execute {
            get id docid from elasticIndex/elasticType
       }.await
    return data
  }
  
  def return_document(docid: String): Future[GetResponse] = {
    val data = client.execute {
            get id docid from elasticIndex/elasticType
       }
    return data
  }
  
  def get_all(params: Array[QueryDefinition], start:Int, limit: Int): SearchResponse = {
   return client.execute {
      search in elasticIndex / elasticType start start limit limit  query {
        bool {
          must(
            params  
          )
        }
      }
    }.await
  }
  
  def insert_document(id: String, params: scala.collection.mutable.Map[String, Object]): Future[IndexResponse] = {
    //note plain old objects used
    return client.execute {
      index into elasticIndex/elasticType id id fields (params) 
    }
  }
  
  def delete_document(id: String) : DeleteResponse = {
    return client.execute {
       delete id id from elasticIndex / elasticType
    }.await
  }
  
  def update_document(docid: String, params: Map[String, String]): Future[UpdateResponse] = {
    return client.execute {
      update(docid).in(elasticIndex/ elasticType).doc(params)
    }
  }
  
  def fuzzy_document_search(conditions: FuzzyQueryDefinition): SearchResponse ={
   return client.execute {
      search in elasticIndex / elasticType query {
        conditions
      }
    }.await
  }
  
  
}
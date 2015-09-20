package com.api.jumiadata.dao

import com.api.jumiadata.utils.Elastic
import org.elasticsearch.action.get.GetResponse
import com.sksamuel.elastic4s.QueryDefinition
import org.elasticsearch.action.search.SearchResponse
import scala.concurrent.Future
import com.sksamuel.elastic4s.FuzzyQueryDefinition

/**
 * @author olalekanelesin
 */
class APIDAO {
  
  def get_doc(id: String): GetResponse ={
    return Elastic.get_document(id)
  }
  
  def search_documents(conditions: Array[QueryDefinition], start: Int, limit: Int): SearchResponse = {
    return Elastic.get_all(conditions, start, limit)
  }
  
  def fetch_all(conditions: Array[QueryDefinition], start: Int, limit: Int): SearchResponse = {
    return Elastic.get_all(conditions, start, limit)
  }
  
  def fuzzy_search(query: FuzzyQueryDefinition) : SearchResponse = {
    return Elastic.fuzzy_document_search(query)
  }
}
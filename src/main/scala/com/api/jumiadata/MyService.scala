package com.api.jumiadata

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import com.api.jumiadata.logic.APILogic
import com.api.jumiadata.utils.APIresponse

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  def actorRefFactory = context


  def receive = runRoute(myRoute ~ testRoute)

}

// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {
  implicit val rejectHandler = RejectionHandler {
    case MissingQueryParamRejection(paramName) :: _ => ctx 
      => ctx.complete(APIresponse.errorResponse(s"Parameter ' $paramName ' is missing", 404))
    
  }
  val apilogic = new APILogic
  val myRoute =
    path("api" / "jumiaaffiliate" / "product-data") {
      get {
         parameters('product_id) { (product_id) =>
           ctx => ctx.complete(apilogic.get_product(product_id))
         }
      }
    } ~ {
      path("api" / "jumiaaffiliate" / "brand-data") {
        get {
          parameters('brand_name) { (brand_name) => 
            ctx => ctx.complete(apilogic.get_brand_products(brand_name))
          }
        }
      }
    } ~ {
      path("api" / "jumiaaffiliate" / "product-search") {
        get {
          parameters('q){ (query) => 
            ctx => ctx.complete(apilogic.product_search(query))  
          }
        }
      }
    } ~ {
      path("api" / "jumiaaffiliate" / "allproducts") {
        get {
          ctx => ctx.complete(apilogic.get_all_products())
        }
      }
    } 

    val testRoute = 
      path("test") {
        get {
          respondWithMediaType(`application/json`) {
            complete {
            <html>
              <body>
                <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>

            }
          }
        }
      }
}
package controllers

import play.api.mvc._
import play.api.libs.json.Json

/**
  * Created by ToshimitsuKugimoto on 3/20/16.
  */
class Tickets extends Controller{

  case class AvailabilityResponse(result: String, ticketQuantity: Option[Long])

  object AvailabilityResponse {
    implicit val responseFormat = Json.format[AvailabilityResponse]
  }

  def ticketsAvailable = Action { request =>
    val availableTickets = 1000
    val response = AvailabilityResponse("ok", Option(availableTickets))
    Ok(Json.toJson(response))
  }
    /*val result = Json.obj(
      "result" -> "ok",
      "ticketQuantity" -> availableTickets
    )
    Ok(result)
  }*/

}

package controllers

import play.api.mvc._
import play.api.libs.json.Json
import com.semisafe.ticketoverlords.Event
import controllers.responses._
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
/**
  * Created by ToshimitsuKugimoto on 3/22/16.
  */
class Events extends Controller{

  def list = Action.async { request =>
    val eventFuture: Future[Seq[Event]] = Event.list

    val response = eventFuture.map { events =>
      Ok(Json.toJson(SuccessResponse(events)))
    }

    response
  }

  def getByID(eventID: Long) = Action.async { request =>
    val eventFuture: Future[Option[Event]] = Event.getByID(eventID)

    eventFuture.map { event =>
      event.fold {
        NotFound(Json.toJson(ErrorResponse(NOT_FOUND, "No event found")))
      } {e =>
        Ok(Json.toJson(SuccessResponse(e)))
      }
    }
  }

  def create = Action.async(parse.json) { request =>
    // parse from json post body
    val incomingBody = request.body.validate[Event]

    incomingBody.fold(error => {
      val errorMessage = s"Invalid JSON ${error}"
      val response = ErrorResponse(ErrorResponse.INVALID_JSON, errorMessage)
      Future.successful(BadRequest(Json.toJson(response)))
    }, { event =>

      //save event and get a copy back
      val createdEventFuture: Future[Event] = Event.create(event)

      createdEventFuture.map { createdEvent =>
        Created(Json.toJson(SuccessResponse(createdEvent)))
      }
    })
  }
}

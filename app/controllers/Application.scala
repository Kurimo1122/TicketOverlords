package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.routing.JavaScriptReverseRouter

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Application @Inject() extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def jsRoutes = Action {implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.Tickets.ticketsAvailable
      )
    )
  }
}

package controllers

import controllers.requests.{GameAppointmentRequest, UpdateGameAppointmentRequest}
import javax.inject.Inject
import mapping.ModelJSONMapping
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repository.GameRepository
import util.LoggerPerClassAware

import scala.concurrent.ExecutionContext

class GameController @Inject()(cc: ControllerComponents,
                               gameRepository: GameRepository)
  extends AbstractController(cc)
  with LoggerPerClassAware
  with ModelJSONMapping {

  implicit def ec: ExecutionContext = cc.executionContext

  def createGameAppointment: Action[GameAppointmentRequest] = Action.async(parse.json[GameAppointmentRequest]) { implicit request =>
    gameRepository.createGameAppointment(request.body).map { lastError =>
      logger.debug(s"Successfully inserted with LastError: $lastError")
      Created
    }
  }

  def findAppointmentById(appointmentId: String): Action[AnyContent] = Action.async {
    gameRepository.findAppointmentById(appointmentId).map {
      case Some(appointment) => Ok(Json.toJson(appointment))
      case None => Status(NOT_FOUND)
    }
  }
//
//  def updateGameAppointment(gameAppointmentId: String): Action[AnyContent] = Action.async(parse.json[UpdateGameAppointmentRequest])
//    gameRepository.findAppointmentById(appointmentId).map {
//      case Some(appointment) => Ok(Json.toJson(appointment))
//      case None
//  }

}

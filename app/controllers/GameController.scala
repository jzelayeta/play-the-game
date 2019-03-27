package controllers

import javax.inject.Inject
import model.{GameAppointment, User}
import play.api.libs.json.{Json, Reads}
import play.api.mvc._
import repository.GameRepository
import util.LoggerPerClassAware

import scala.concurrent.ExecutionContext

class GameController @Inject()(cc: ControllerComponents,
                               gameRepository: GameRepository)
  extends AbstractController(cc)
  with LoggerPerClassAware {

  implicit def ec: ExecutionContext = cc.executionContext

  implicit val userReader: Reads[User] = Json.reads[User]
  implicit val gameAppointmentReader: Reads[GameAppointment] = Json.reads[GameAppointment]

  def createGameAppointment: Action[GameAppointment] = Action.async(parse.json[GameAppointment]) { request =>
    gameRepository.createGameAppointment(request.body).map { lastError =>
      logger.debug(s"Successfully inserted with LastError: $lastError")
      Created
    }
  }

}

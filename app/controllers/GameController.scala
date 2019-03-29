package controllers

import javax.inject.Inject
import model.{GameAppointment, GameSettings, Sport, User}
import play.api.libs.json.{Json, Reads, __}
import play.api.mvc._
import repository.GameRepository
import util.LoggerPerClassAware
import play.api.libs.json.{Reads, __}
import play.api.libs.functional.syntax._

import scala.concurrent.ExecutionContext

class GameController @Inject()(cc: ControllerComponents,
                               gameRepository: GameRepository)
  extends AbstractController(cc)
  with LoggerPerClassAware {

  implicit def ec: ExecutionContext = cc.executionContext

  implicit val userReader: Reads[User] = Json.reads[User]
  implicit val gameAppointmentReader: Reads[GameAppointment] = Json.reads[GameAppointment]

  implicit val notifReads: Reads[GameSettings] = (
    (__ \ "sport").read[String].map(Sport(_)) and
      (__ \ "currentPlayers").read[List[User]]
    ) (GameSettings)

  def createGameAppointment: Action[GameAppointment] = Action.async(parse.json[GameAppointment]) { implicit request =>
    gameRepository.createGameAppointment(request.body).map { lastError =>
      logger.debug(s"Successfully inserted with LastError: $lastError")
      Created
    }
  }

}

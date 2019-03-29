package repository

import javax.inject.Inject
import model.{GameAppointment, GameSettings, Sport, User}
import play.api.libs.json.{JsValue, Json, Writes}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONDocumentWriter, Macros}

import scala.concurrent.{ExecutionContext, Future}

class GameRepository @Inject()(val mongoConnection: MongoConnection)
                              (implicit val ec: ExecutionContext) {

  private implicit val userMapping: BSONDocumentWriter[User] = Macros.writer[User]
  implicit object SportWriter extends BSONDocumentWriter[Sport] {
    def write(sport: Sport): BSONDocument =
      BSONDocument("sport" -> sport.toString)
  }


  private implicit val gameSettingsMapping: BSONDocumentWriter[GameSettings] = Macros.writer[GameSettings]
  private implicit val productIdMapping: BSONDocumentWriter[GameAppointment] = Macros.writer[GameAppointment]

  def appointmentsCollections: Future[BSONCollection] =
    mongoConnection.database.map(_.collection("appointments"))


  def createGameAppointment(gameAppointment: GameAppointment): Future[WriteResult] =
    appointmentsCollections.flatMap(_.insert.one(gameAppointment))

}

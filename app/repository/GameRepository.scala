package repository

import javax.inject.Inject
import model.{GameAppointment, User}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocumentWriter, Macros}

import scala.concurrent.{ExecutionContext, Future}

class GameRepository @Inject()(val mongoConnection: MongoConnection)
                              (implicit val ec: ExecutionContext) {

  private implicit val userMapping: BSONDocumentWriter[User] = Macros.writer[User]
  private implicit val productIdMapping: BSONDocumentWriter[GameAppointment] = Macros.writer[GameAppointment]

  def appointmentsCollections: Future[BSONCollection] =
    mongoConnection.database.map(_.collection("appointments"))


  def createGameAppointment(gameAppointment: GameAppointment): Future[WriteResult] =
    appointmentsCollections.flatMap(_.insert.one(gameAppointment))

}

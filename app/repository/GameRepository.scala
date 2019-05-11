package repository

import controllers.requests.GameAppointmentRequest
import javax.inject.Inject
import mapping.ModelBSONMapping
import model.GameAppointment
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.{ExecutionContext, Future}

class GameRepository @Inject()(val mongoConnection: MongoConnection)
                              (implicit val ec: ExecutionContext) extends ModelBSONMapping {


  val appointmentsCollections: Future[BSONCollection] =
    mongoConnection.database.map(_.collection("appointments"))


  def createGameAppointment(gameAppointment: GameAppointmentRequest): Future[GameAppointment] = {
    val document: BSONDocument = BSONDocument("_id" -> BSONObjectID.generate(),
      "author" -> gameAppointment.author,
      "appointmentDate" -> gameAppointment.appointmentDate,
      "createdDate" -> gameAppointment.createdDate,
      "game" -> gameAppointment.game)

    appointmentsCollections.flatMap(_.insert.one(document).map(_ => document.as[GameAppointment]))
  }

  def findAppointmentById(gameAppointmentId: String): Future[Option[GameAppointment]] = {
    BSONObjectID.parse(gameAppointmentId).map { objId =>
      val query = BSONDocument("_id" -> objId)

      appointmentsCollections.flatMap(_.find(query).one[GameAppointment])

    } getOrElse Future.successful(None)

  }

}

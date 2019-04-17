package mapping

import controllers.requests.GameAppointmentRequest
import model._
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONLong, BSONObjectID, BSONString, BSONValue, BSONWriter, Macros}

trait ModelBSONMapping {

  implicit val userBsonWrites: BSONDocumentWriter[User] = Macros.writer[User]
  implicit val resultMapping: BSONDocumentWriter[Result] = Macros.writer[Result]
  implicit val gameAppointmentRequestBsonWriter: BSONDocumentWriter[GameAppointmentRequest] = Macros.writer[GameAppointmentRequest]

  implicit val userReads: BSONDocumentReader[User] = Macros.reader[User]
  implicit val resultReads: BSONDocumentReader[Result] = Macros.reader[Result]

  implicit object GameAppointmentRequestWriter extends BSONDocumentWriter[GameAppointmentRequest] {
    override def write(gameAppointment: GameAppointmentRequest): BSONDocument = {
      BSONDocument("_id" -> BSONObjectID.generate(),
        "author" -> gameAppointment.author,
        "appointmentDate" -> gameAppointment.appointmentDate,
        "createdDate" -> gameAppointment.createdDate,
        "game" -> gameAppointment.game)
    }
  }

  implicit object SportWriter extends BSONWriter[Sport, BSONValue] {
    override def write(sport: Sport) =  BSONString(sport.toString)
  }

  implicit object SportReader extends BSONDocumentReader[Sport] {
    override def read(bson: BSONDocument): Sport = {
      bson.getAs[BSONString]("sport").map(_.value).map(Sport(_)).get
    }
  }


  implicit object GameReader extends BSONDocumentReader[Game] {
    override def read(bson: BSONDocument): Game = {
      val sport = bson.getAs[BSONString]("sport").map(_.value).map(Sport(_)).get
      val users = bson.getAs[List[User]]("players").get
      val maybeResult = bson.getAs[Result]("result")
      Game(sport, users, maybeResult)
    }
  }

  implicit object GameAppointmentReader extends BSONDocumentReader[GameAppointment] {
    override def read(bson: BSONDocument): GameAppointment = {
      val appointmentId = bson.getAs[BSONObjectID]("_id").map(_.stringify).get
      val user = bson.getAs[User]("author").get
      val appointmentDate = bson.getAs[BSONLong]("appointmentDate").map(_.value).get
      val createdDate = bson.getAs[BSONLong]("createdDate").map(_.value).get
      val game = bson.getAs[Game]("game").get
      GameAppointment(appointmentId, user, appointmentDate, createdDate, game)
    }
  }

  implicit val gameSettingsMapping: BSONDocumentWriter[Game] = Macros.writer[Game]
}

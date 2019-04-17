package mapping

import controllers.requests.GameAppointmentRequest
import model._
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait ModelJSONMapping {

  implicit val userJsonReads: Reads[User] = Json.reads[User]
  implicit val userJsonWrites: Writes[User] = Json.writes[User]


  implicit val resultReades: Reads[Result] = Json.reads[Result]
  implicit val gameAppointmentReader: Reads[GameAppointment] = Json.reads[GameAppointment]

 implicit val gameReads: Reads[Game] = (
    (__ \ "sport").read[String].map(Sport(_)) and
      (__ \ "players").read[List[User]] and
      (__ \ "result").readNullable[Result]
    ) (Game)

  implicit val gameAppointmentRequestReads: Reads[GameAppointmentRequest] = (
    (__ \ "author").read[User] and
      (__ \ "appointmentDate").read[Long] and
        (__ \ "createdDate").read[Long] and
          (__ \ "game").read[Game]
    ) (GameAppointmentRequest)

  implicit val sportReads: Reads[Sport] = (__ \ "sport")
    .read[String].map {
    Sport(_)
  }

  implicit val sportWrites: Writes[Sport] = (o: Sport) => {
    Json.obj(
      "sport" → o.toString
    )
  }

  implicit val maybesportWrites: Writes[Option[Sport]] = (o: Option[Sport]) => o match {
    case Some(sport) => implicitly[Writes[Sport]].writes(sport)
    case None => JsNull
  }

  implicit def optionFormat[T: Format]: Format[Option[T]] = new Format[Option[T]]{
    override def reads(json: JsValue): JsResult[Option[T]] = json.validateOpt[T]

    override def writes(o: Option[T]): JsValue = o match {
      case Some(t) ⇒ implicitly[Writes[T]].writes(t)
      case None ⇒ JsNull
    }
  }

  implicit val resultWriter: Writes[Result] = Json.writes[Result]
  implicit val gameWriter: Writes[Game] = Json.writes[Game]
  implicit val gameAppointmentWriter: Writes[GameAppointment] = Json.writes[GameAppointment]

}

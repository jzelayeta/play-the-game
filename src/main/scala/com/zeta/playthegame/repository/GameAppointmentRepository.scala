package com.zeta.playthegame.repository

import com.zeta.playthegame.GameAppointmentRequest
import com.zeta.playthegame.model.GameAppointment
import com.zeta.playthegame.util.LoggerPerClassAware
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document
import com.zeta.playthegame.repository.Entities._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object GameAppointmentRepository extends LoggerPerClassAware {
  import MongoConnection._

  def getAppointmentById(id: String) = {
    appointmentsCollection.find(Document("_id" -> new ObjectId(id)))
      .first()
      .map(_.toModel)
      .headOption()
  }

  def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest) = {
    val appointment = GameAppointmentDocument(new ObjectId(),
      gameAppointmentRequest.authorId,
      gameAppointmentRequest.appointmentDate,
      gameAppointmentRequest.createdDate,
      GameDocument(gameAppointmentRequest.game.sport.value, gameAppointmentRequest.game.players, gameAppointmentRequest.game.result))
    appointmentsCollection.insertOne(appointment)
      .head
      .map(_ => appointment.toModel)
  }

  def deleteGameAppointment(id: String): Future[Option[GameAppointment]] = {
    appointmentsCollection.findOneAndDelete(Document("_id" -> new ObjectId(id)))
      .map(_.toModel)
      .headOption()
  }

}

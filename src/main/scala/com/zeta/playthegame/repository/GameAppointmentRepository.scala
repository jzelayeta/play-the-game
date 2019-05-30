package com.zeta.playthegame.repository

import com.zeta.playthegame.GameAppointmentRequest
import com.zeta.playthegame.repository.Entities._
import com.zeta.playthegame.util.LoggerPerClassAware
import org.mongodb.scala.Completed
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.ExecutionContext.Implicits.global

object GameAppointmentRepository extends LoggerPerClassAware {
  import MongoConnection._

  def getAppointmentById(id: String) = {
    appointmentsCollection.find(Document("_id" -> new ObjectId(id)))
      .first()
      .headOption()
  }

  def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest) = {
    val appointment = GameAppointmentDocument(new ObjectId(),
      gameAppointmentRequest.authorId,
      gameAppointmentRequest.appointmentDate,
      gameAppointmentRequest.createdDate,
      GameDocument(gameAppointmentRequest.game.sport.value, gameAppointmentRequest.game.players, gameAppointmentRequest.game.result))
    appointmentsCollection.insertOne(appointment)
      .head()
      .map {
        case Completed() => Some(appointment)
        case _           => None
      }
  }

  def deleteGameAppointment(id: String) = {
    appointmentsCollection.findOneAndDelete(Document("_id" -> new ObjectId(id)))
      .headOption()
  }

}

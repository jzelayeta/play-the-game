package com.zeta.playthegame.repository

import com.zeta.playthegame.GameAppointmentRequest
import com.zeta.playthegame.model.GameAppointment
import com.zeta.playthegame.util.LoggerPerClassAware
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.ExecutionContext.Implicits.global

object GameAppointmentRepository extends LoggerPerClassAware {
  import MongoConnection._

  private val appointmentsCollection: MongoCollection[GameAppointment] = database.getCollection("appointments")

  def getAppointmentById(id: String) = {
    appointmentsCollection.find(Document("_id" -> new ObjectId(id)))
      .first()
      .head()
      .map(Option(_))
  }
  def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest) = {
    val appointment = GameAppointment(gameAppointmentRequest.author,
      gameAppointmentRequest.appointmentDate,
      gameAppointmentRequest.createdDate,
      gameAppointmentRequest.game)
    appointmentsCollection.insertOne(appointment)
      .head
      .map(_ => appointment)
  }

  def deleteGameAppointment(id: String) = {
    appointmentsCollection.findOneAndDelete(Document("_id" -> new ObjectId(id)))
      .head()
      .map(Option(_))
  }

}

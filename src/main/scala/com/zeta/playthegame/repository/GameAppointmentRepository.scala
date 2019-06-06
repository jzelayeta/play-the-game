package com.zeta.playthegame.repository

import com.zeta.playthegame.GameAppointmentRequest
import com.zeta.playthegame.repository.Entities._
import com.zeta.playthegame.util.IOExt._
import com.zeta.playthegame.util.LoggerPerClassAware
import org.mongodb.scala.Completed
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.ExecutionContext

class GameAppointmentRepository(mongoConnection: MongoConnection)(implicit executionContext: ExecutionContext) extends LoggerPerClassAware {

  def getAppointmentById(id: String) = mongoConnection.appointmentsCollection map {
      _.find(Document("_id" -> new ObjectId(id)))
        .first()
        .map(_.toModel)
        .headOption()
    } toIO


  def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest) = {
      val appointment = GameAppointmentDocument(new ObjectId(),
        gameAppointmentRequest.authorId,
        gameAppointmentRequest.appointmentDate,
        gameAppointmentRequest.createdDate,
        GameDocument(gameAppointmentRequest.game.sport.value, gameAppointmentRequest.game.players, gameAppointmentRequest.game.result))

        mongoConnection.appointmentsCollection.map {
          _.insertOne(appointment)
            .head()
            .map {
              case Completed() => Some(appointment.toModel)
              case _ => None
            }
        } toIO
      }

    def deleteGameAppointment(id: String) = mongoConnection.appointmentsCollection.map {
        _.findOneAndDelete(Document("_id" -> new ObjectId(id)))
          .map(_.toModel)
          .headOption()
      } toIO

}

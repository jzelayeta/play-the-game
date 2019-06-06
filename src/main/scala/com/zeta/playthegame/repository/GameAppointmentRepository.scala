package com.zeta.playthegame.repository

import cats.effect.IO
import com.zeta.playthegame.GameAppointmentRequest
import com.zeta.playthegame.repository.Entities._
import com.zeta.playthegame.util.LoggerPerClassAware
import org.mongodb.scala.Completed
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.ExecutionContext

class GameAppointmentRepository(mongoConnection: MongoConnection)(implicit executionContext: ExecutionContext) extends LoggerPerClassAware {

  def getAppointmentById(id: String) = IO.fromFuture {
    mongoConnection.appointmentsCollection map {
      _.find(Document("_id" -> new ObjectId(id)))
        .first()
        .map(_.toModel)
        .headOption()
    }
  }
    def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest) = {
      val appointment = GameAppointmentDocument(new ObjectId(),
        gameAppointmentRequest.authorId,
        gameAppointmentRequest.appointmentDate,
        gameAppointmentRequest.createdDate,
        GameDocument(gameAppointmentRequest.game.sport.value, gameAppointmentRequest.game.players, gameAppointmentRequest.game.result))
      IO.fromFuture {
        mongoConnection.appointmentsCollection.map {
          _.insertOne(appointment)
            .head()
            .map {
              case Completed() => Some(appointment.toModel)
              case _ => None
            }
        }
      }
    }

    def deleteGameAppointment(id: String) = IO.fromFuture {
      mongoConnection.appointmentsCollection.map {
        _.findOneAndDelete(Document("_id" -> new ObjectId(id)))
          .map(_.toModel)
          .headOption()
      }
    }

}

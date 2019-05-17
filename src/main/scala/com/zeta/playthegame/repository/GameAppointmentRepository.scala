package com.zeta.playthegame.repository

import com.zeta.playthegame.GameAppointmentRequest
import com.zeta.playthegame.model.GameAppointment
import com.zeta.playthegame.util.LoggerPerClassAware
import org.mongodb.scala.{Completed, MongoCollection, Observer}


object GameAppointmentRepository extends LoggerPerClassAware {
  import MongoConnection._

  private val appointmentsCollection: MongoCollection[GameAppointment] = database.getCollection("appointments")

  def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest) = {
    val appointment = GameAppointment(gameAppointmentRequest.author,
      gameAppointmentRequest.appointmentDate,
      gameAppointmentRequest.createdDate,
      gameAppointmentRequest.game)
    appointmentsCollection.insertOne(appointment).subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = logger.info(s"onNext: $result")

      override def onError(e: Throwable): Unit = logger.info(s"onError: $e")

      override def onComplete(): Unit = logger.info("onComplete")
    })
    appointment
  }

}

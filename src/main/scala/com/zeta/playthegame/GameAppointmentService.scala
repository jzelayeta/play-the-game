package com.zeta.playthegame

import cats.effect.IO
import com.zeta.playthegame.repository.GameAppointmentRepository

import scala.concurrent.ExecutionContext.Implicits.global


trait GameAppointmentService {
  val gameAppointment: GameAppointmentService.Service
}

object GameAppointmentService {

  trait Service {

    def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest) = IO.fromFuture(IO {
      for {
        result <- GameAppointmentRepository.addGameAppointment(gameAppointmentRequest)
      } yield {
        result match {
          case Some(document) => Some(document.toModel)
          case _              => None
        }
      }
    })


    def getGameAppointment(key: String) = IO.fromFuture(IO {
      for {
        maybeDocument <- GameAppointmentRepository.getAppointmentById(key)
      } yield {
        maybeDocument.map(_.toModel)
      }
    })

    def deleteGameAppointment(key: String) = IO.fromFuture( IO  {
      for {
        maybeDocument <- GameAppointmentRepository.deleteGameAppointment(key)
      } yield {
        maybeDocument.map(_.toModel)
      }
    })

  }

  trait Live extends Service

  object Live extends Live

}

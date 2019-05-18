package com.zeta.playthegame

import cats.effect.IO
import com.zeta.playthegame.repository.GameAppointmentRepository


trait GameAppointmentService {
  val gameAppointment: GameAppointmentService.Service
}

object GameAppointmentService {

  trait Service {

    def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest) = IO(GameAppointmentRepository.addGameAppointment(gameAppointmentRequest))

    def getGameAppointment(key: String) = IO(GameAppointmentRepository.getAppointmentById(key))

    def deleteGameAppointment(key: String) = IO(GameAppointmentRepository.deleteGameAppointment(key))

  }

  trait Live  extends Service
  object Live extends Live

}

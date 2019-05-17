package com.zeta.playthegame

import cats.effect.IO
import com.zeta.playthegame.repository.GameAppointmentRepository


trait GameAppointment {
  val gameAppointment: GameAppointment.Service
}

object GameAppointment {

  trait Service {

//    def get(key: String) = IO(GameAppointmentRepository.getPerson(key))

    def addGameAppointment(gameAppointmentRequest: GameAppointmentRequest): IO[model.GameAppointment] = IO(GameAppointmentRepository.addGameAppointment(gameAppointmentRequest))

  }

  trait Live  extends Service
  object Live extends Live

}

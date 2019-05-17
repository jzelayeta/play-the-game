package com.zeta.playthegame

import cats.effect.IO
import com.zeta.playthegame.repository.PersonRepository


trait GameAppointment {
  val gameAppointment: GameAppointment.Service
}

object GameAppointment {

  trait Service {

    def get(key: String) = IO(PersonRepository.getPerson(key))

    def add(key:String, value: String) = IO(PersonRepository.addPerson(key, value))

  }

  trait Live  extends Service
  object Live extends Live

}

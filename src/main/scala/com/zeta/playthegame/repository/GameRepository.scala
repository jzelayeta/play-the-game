package com.zeta.playthegame.repository

import cats.data.ReaderT
import com.zeta.playthegame.model.GameAppointment

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object GameRepository {
  def addGame(appointmentId: String, gameAppointment: GameAppointment): ReaderT[Future, GameStore, GameAppointment] = ReaderT { gameStore =>
    for {
      appointment <- gameStore.create(appointmentId, gameAppointment)
    } yield appointment
  }
}

//package com.zeta.playthegame
//
//import cats.effect.IO
//import com.zeta.playthegame.repository.GameAppointmentRepository
//
//import scala.concurrent.ExecutionContext.Implicits.global
//
//class GameAppointmentService(gameAppointmentRepository: GameAppointmentRepository) {
//
//    def getGameAppointment(key: String) = {
//      for {
//        bla           <- gameAppointmentRepository.getAppointmentById(key)
//        maybeDocument <- bla
//      } yield {
//        ma
//      }
//    }
//}

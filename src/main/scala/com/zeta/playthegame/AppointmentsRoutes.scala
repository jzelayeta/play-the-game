package com.zeta.playthegame


import cats.effect.IO
import com.zeta.playthegame.repository.AppointmentRepository
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl

class AppointmentsRoutes(gameAppointmentRepository: AppointmentRepository) {

  def routes: HttpRoutes[IO] = {
    object dsl extends Http4sDsl[IO]
    import dsl._

    HttpRoutes.of[IO] {
      case req @ POST -> Root / "appointments" => req.decode[AppointmentRequest] {
        gameAppointmentRepository.addAppointment(_) flatMap {
            case Some(appointment) => Created(appointment)
            case _                 => InternalServerError()
          }
      }

      case GET -> Root / "appointments" / id =>
        gameAppointmentRepository.getAppointmentById(id)
          .flatMap {
            case Some(appointment) => Ok(appointment)
            case _                 => NotFound()
          }

      case DELETE -> Root / "appointments" / id =>
        gameAppointmentRepository.deleteAppointment(id)
          .flatMap {
            case Some(appointment) => Ok(appointment)
            case _                 => NotFound()
          }
    }
  }
}
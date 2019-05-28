package com.zeta.playthegame


import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl

object PlaythegameRoutes {

  def gameAppointmentRoutes: HttpRoutes[IO] = {
    object dsl extends Http4sDsl[IO]
    import dsl._

    HttpRoutes.of[IO] {
      case req @ POST -> Root / "appointments" => req.decode[GameAppointmentRequest] {
        GameAppointmentService.Live.addGameAppointment(_)
          .flatMap(gap => IO.fromFuture(IO(gap))
            .flatMap(Ok(_)))
      }

      case GET -> Root / "appointments" / id =>
        GameAppointmentService.Live.getGameAppointment(id)
          .flatMap(gap => IO.fromFuture(IO(gap)))
          .flatMap({
            case Some(appointment) => Ok(appointment)
            case _                 => NotFound()
          })

      case DELETE -> Root / "appointments" / id =>
        GameAppointmentService.Live.deleteGameAppointment(id)
          .flatMap(gap => IO.fromFuture(IO(gap)))
          .flatMap({
            case Some(appointment) => Ok(appointment)
            case _                 => NotFound()
          })
    }
  }
}
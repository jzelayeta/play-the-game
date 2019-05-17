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
      case req @ POST -> Root / "appointment" => req.decode[GameAppointmentRequest](GameAppointment.Live.addGameAppointment(_).flatMap(Ok(_)))
    }
  }
}
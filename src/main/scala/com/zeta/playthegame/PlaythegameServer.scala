package com.zeta.playthegame


import cats.effect.{ContextShift, IO, Timer}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

object PlaythegameServer {

  val httpApp = PlaythegameRoutes.gameAppointmentRoutes.orNotFound

  def server(implicit T: Timer[IO], C: ContextShift[IO]) =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpApp)
      .serve
}
package com.zeta.playthegame

import cats.effect.{ConcurrentEffect, Effect, ExitCode, IO, IOApp, Timer, ContextShift}
import cats.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger
import fs2.Stream
import scala.concurrent.ExecutionContext.global

object PlaythegameServer {

//  val httpApp = (
//    PlaythegameRoutes.helloWorldRoutes[F](helloWorldAlg)
//    ).orNotFound

  def server(implicit T: Timer[IO], C: ContextShift[IO]) =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(PlaythegameRoutes.lalaRoutes.orNotFound)
      .serve

  def stream[F[_]: ConcurrentEffect](implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
    for {
      client <- BlazeClientBuilder[F](global).stream
      helloWorldAlg = HelloWorld.impl[F]
      jokeAlg = Jokes.impl[F](client)
      gameAppint = GameAppointment.impl[F]

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract a segments not checked
      // in the underlying routes.
      httpApp = (
        PlaythegameRoutes.helloWorldRoutes[F](helloWorldAlg) <+>
        PlaythegameRoutes.jokeRoutes[F](jokeAlg) <+>
        PlaythegameRoutes.gameAppointmentRoutes[F](gameAppint)
      ).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- BlazeServerBuilder[F]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
  }.drain
}
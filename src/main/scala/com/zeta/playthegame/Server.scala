package com.zeta.playthegame


import java.util.concurrent.Executors

import cats.effect.{ContextShift, IO, Timer}
import com.typesafe.config.ConfigFactory
import com.zeta.playthegame.repository.{AppointmentRepository, MongoConnection}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext

object Server {

  private lazy val poolSize = ConfigFactory.defaultApplication().getConfig("mongodb").getInt("poolSize")

  implicit lazy val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(poolSize)) //Exclusive ExecutionPool for Database access only

  private lazy val repository = new AppointmentRepository(MongoConnection)

  private lazy val httpApp = new AppointmentsRoutes(repository).routes.orNotFound

  def server(implicit T: Timer[IO], C: ContextShift[IO]) =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpApp)
      .serve
}
package com.zeta.playthegame.integration

import cats.effect.{ContextShift, Fiber, IO, Timer}
import org.http4s.{HttpRoutes, Uri}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.scalatest.{BeforeAndAfterAll, TestSuite}

trait ServerApp extends BeforeAndAfterAll { self: TestSuite =>

  implicit val ec                   = scala.concurrent.ExecutionContext.Implicits.global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec)
  implicit val timer: Timer[IO]     = IO.timer(ec)

  val baseUri = Uri.unsafeFromString("http://localhost:8080")
  val router: HttpRoutes[IO]
  var serverTask: Fiber[IO, Nothing] = _

  private def createServer  = {
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(router.orNotFound)
  }

  def startServer = serverTask = createServer.resource.use(_ => IO.never).start.unsafeRunSync()

  def stopServer = serverTask.cancel
}

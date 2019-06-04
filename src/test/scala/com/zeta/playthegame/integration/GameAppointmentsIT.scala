package com.zeta.playthegame.integration
import cats.effect.{ContextShift, IO, IOApp, Timer}
import com.zeta.playthegame.PlaythegameRoutes
import com.zeta.playthegame.model.Sport.FootballFive
import com.zeta.playthegame.model.{Game, GameAppointment}
import com.zeta.playthegame.util.Generators
import io.circe.Json
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.{Method, Request, Uri}
import org.scalatest.{FlatSpecLike, Matchers}
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.implicits._
import org.http4s.circe._

import scala.concurrent.ExecutionContext.Implicits.global

class GameAppointmentsIT extends FlatSpecLike with Matchers with Generators {

  implicit val ec                   = scala.concurrent.ExecutionContext.Implicits.global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec)
  implicit val timer: Timer[IO]     = IO.timer(ec)


  val serviceUri = Uri.unsafeFromString("http://localhost:8080")
  createServer().resource.use(_ => IO.never).start.unsafeRunSync()

  "GET appointment" should "bla" in {
    val id = "5cef46644260d974b78e89d9"
    val request  = Request[IO](Method.GET, serviceUri / "appointments" / id)
    val response = BlazeClientBuilder[IO](global).resource.use(_.expect[Json](request))
    response.unsafeRunSync() shouldBe GameAppointment(randomStringId,
      randomStringId,
      1L,
      2L,
      Game(FootballFive, List.empty)).asJson
  }

  private def createServer() = {
    BlazeServerBuilder[IO]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(PlaythegameRoutes.gameAppointmentRoutes.orNotFound)
    }
}

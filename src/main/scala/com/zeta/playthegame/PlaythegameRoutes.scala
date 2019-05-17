package com.zeta.playthegame

import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._
import cats.effect.{IO, Sync}
import cats.implicits._
import com.zeta.playthegame.repository.GameStore
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl


object PlaythegameRoutes {

  def lalaRoutes: HttpRoutes[IO] = {

    object dsl extends Http4sDsl[IO]
    import dsl._

    HttpRoutes.of[IO] {
      case GET -> Root / "zeta" => Ok(Lala.Live.zeta)
      case GET -> Root / "palan" => Ok(Lala.Live.palan)
    }

  }

  def gameAppointmentRoutes: HttpRoutes[IO] = {
    object dsl extends Http4sDsl[IO]
    import dsl._

    lazy val gameStore: GameStore = new GameStore()
    case class AppointmentRequest(id: String, value: String)
    HttpRoutes.of[IO] {
      case GET -> Root / "appointment" / id => Ok(GameAppointment.Live.get(id))
      case req @ POST -> Root / "appointment" => req.decode[AppointmentRequest]{ data =>
        Ok(GameAppointment.Live.add(data.id, data.value))
      }

      //      case req @ POST -> Root / "appointment" =>
      //        req.decode[GameAppointmentRequest] { data =>
      //          val i = Random.nextInt(1000).toString
      //          val gap = com.zeta.playthegame.model.GameAppointment(i, data.author, data.createdDate, data.createdDate, data.game)
      //          val eventualAppointment = GameRepository.addGame(Random.nextInt(1000).toString, gap)
      //          Ok(IO.fromFuture(IO(eventualAppointment.run(gameStore))).unsafeRunSync().asJson)
      //        }
    }
  }
}
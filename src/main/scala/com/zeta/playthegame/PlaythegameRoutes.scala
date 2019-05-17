package com.zeta.playthegame

import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._
import cats.effect.{IO, Sync}
import cats.implicits._
import com.zeta.playthegame.repository.PersonStore
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl


object PlaythegameRoutes {

  def gameAppointmentRoutes: HttpRoutes[IO] = {
    object dsl extends Http4sDsl[IO]
    import dsl._

    lazy val personStore: PersonStore = new PersonStore()

    case class AppointmentRequest(id: String, value: String)

    HttpRoutes.of[IO] {
      case GET -> Root / "appointment" / id => Ok(IO.fromFuture(GameAppointment.Live.get(id).map(_.run(personStore))))

      case req @ POST -> Root / "appointment" => req.decode[AppointmentRequest]{ data =>
        val value = IO.fromFuture(GameAppointment.Live.add(data.id, data.value).map(_.run(personStore)))
        Ok(value)
      }
    }
  }
}
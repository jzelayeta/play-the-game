package com.zeta.playthegame

import cats.effect.{IO, Sync}
import cats.implicits._
import com.zeta.playthegame.repository.{GameRepository, GameStore}
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes

import io.circe.generic.auto._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._


object PlaythegameRoutes {

  def lalaRoutes: HttpRoutes[IO] = {

    val dsl = new Http4sDsl[IO] {}
    import dsl._

    HttpRoutes.of[IO] {
      case GET -> Root / "zeta" => Ok(Lala.Live.zeta)
      case GET -> Root / "palan" => Ok(Lala.Live.palan)
    }

  }

  def jokeRoutes[F[_]: Sync](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "joke" =>
        for {
          joke <- J.get
          resp <- Ok(joke)
        } yield resp
    }
  }

  def helloWorldRoutes[F[_]: Sync](H: HelloWorld[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        for {
          greeting <- H.hello(HelloWorld.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }
  }

  def gameAppointmentRoutes[F[_]: Sync](G: GameAppointment[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    lazy val gameStore: GameStore = new GameStore()
    case class AppointmentRequest(id: String)
    HttpRoutes.of[F] {
      case GET -> Root / "appointment" =>
        for {
          appointment <- G.getAppointmentId
          resp        <- Ok(appointment)
        } yield resp

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
package com.zeta.playthegame

import cats.Applicative

import scala.util.Random
import cats.implicits._
import com.zeta.playthegame.HelloWorld.Greeting
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf

trait GameAppointment[F[_]]{
  def getAppointmentId: F[GameAppointment.GameAppointmentId]
}

object GameAppointment {
  implicit def apply[F[_]](implicit ev: GameAppointment[F]): GameAppointment[F] = ev

  final case class GameAppointmentId(id: Int) extends AnyVal

  object GameAppointmentId {
    implicit val gameAppointmentIdEncoder: Encoder[GameAppointmentId] = new Encoder[GameAppointmentId] {
      final def apply(a: GameAppointmentId): Json = Json.obj(
        ("appointment", Json.fromInt(a.id)),
      )
    }
    implicit def gameAppointmentEntityEncoder[F[_]: Applicative]: EntityEncoder[F, GameAppointmentId] =
      jsonEncoderOf[F, GameAppointmentId]
  }

  def impl[F[_]: Applicative]: GameAppointment[F] = new GameAppointment[F]{
    override def getAppointmentId: F[GameAppointment.GameAppointmentId] =
      GameAppointmentId(Random.nextInt()).pure[F]
  }
}

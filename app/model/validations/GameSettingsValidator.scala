package model.validations
import cats._

import cats.data._
import cats.implicits._

import model.GameSettings

trait GameSettingsValidator[F[_]] {
  def createValidUser(name: String, age: Int, email: String): F[GameSettings]
}

object GameSettingsValidator {
  def apply[F[_]](implicit ev: GameSettingsValidator[F]): GameSettingsValidator[F] = ev

  def validate[F[_]: GameSettingsValidator, E](name: String,
                                       age: Int,
                                       email: String): F[GameSettings] =
    GameSettingsValidator[F].createValidUser(name, age, email)
}
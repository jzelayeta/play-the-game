package com.zeta.playthegame

import com.zeta.playthegame.model.{Game, User}

case class UserRequest(id: Option[String] = None,
                       name: Option[String] = None,
                       lastName: Option[String] = None,
                       nickname: Option[String] = None)


case class GameAppointmentRequest(author: User,
                                  appointmentDate: Long,
                                  createdDate: Long,
                                  game: Game)

case class UpdateGameAppointmentRequest(author: Option[User] = None,
                                        appointmentDate: Option[Long] = None,
                                        game: Option[Game] = None) {
  require(Seq(author,appointmentDate, game).flatten.nonEmpty)
}
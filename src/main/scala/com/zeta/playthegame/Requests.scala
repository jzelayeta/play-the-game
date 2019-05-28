package com.zeta.playthegame

import com.zeta.playthegame.model.Game

case class ResultRequest(winningTeam: Set[String], loserTeam: Set[String], winningTeamPoints: Int, loserTeamPoints: Int)


case class GameAppointmentRequest(authorId: String,
                                  appointmentDate: Long,
                                  createdDate: Long,
                                  game: Game)

case class UpdateGameAppointmentRequest(author: Option[String] = None,
                                        appointmentDate: Option[Long] = None,
                                        game: Option[Game] = None) {
  require(Seq(author,appointmentDate, game).flatten.nonEmpty)
}
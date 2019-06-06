package com.zeta.playthegame

import com.zeta.playthegame.model.Game

case class ResultRequest(winningTeam: Set[String], loserTeam: Set[String], winningTeamPoints: Int, loserTeamPoints: Int)


case class AppointmentRequest(authorId: String,
                              appointmentDate: Long,
                              createdDate: Long,
                              game: Game)

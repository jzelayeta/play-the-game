package com.zeta.playthegame.model

import io.circe.{Decoder, Encoder}


case class Team(id: String, name: String, players: List[String])

sealed trait Sport {
  val maxPlayers: Int
  val value: String
}

object Sport {

  case object FootballFive extends Sport {
    override val maxPlayers: Int = 10
    override val value: String = "footballFive"
  }

  case object FootballEleven extends Sport {
    override val maxPlayers: Int = 22
    override val value: String = "footballEleven"
  }

  case object Basketball extends Sport {
    override val maxPlayers: Int = 10
    override val value: String = "basketball"
  }

  case object TennisSingle extends Sport {
    override val maxPlayers: Int = 2
    override val value: String = "tennisSingle"
  }

  case object TennisDouble extends Sport {
    override val maxPlayers: Int = 4
    override val value: String = "tennisDouble"
  }

  def apply(sportName: String): Sport = sportName match {
    case "footballFive" => FootballFive
    case "footballEleven" => FootballEleven
    case "basketball" => Basketball
    case "tennisSingle" => TennisSingle
  }

  implicit val encoder: Encoder[Sport] = Encoder.encodeString contramap (_.value)

  implicit val decoder: Decoder[Sport] = Decoder.decodeString map (Sport(_))
}


case class Appointment(appointmentId: String, authorId: String, appointmentDate: Long, createdDate: Long, game: Game) {

  def addPlayer(user: String): Appointment = this.copy(game = game.addPlayer(user))

  def getPlayers: List[String] = this.game.players

  def removePlayer(user: String): Appointment = this.copy(game = game.removePlayer(user))

  def changeAuthor(user: String): Appointment = this.copy(authorId = user)

  def addResult(aResult: Result): Appointment = this.copy(game = game.copy(result = Some(aResult)))

  def sport = game.sport

  def changeSport(newSport: Sport) = this.copy(game = game.copy(sport = newSport))

  def winner = game.result.map(_.winningTeam)

  def loser = game.result.map(_.loserTeam)
}

case class FinishedGame(id: String, appointmentId: String, author: String, timestamp: Long, game: Game)

case class Result(winningTeam: List[String], loserTeam: List[String], winningTeamPoints: Int, loserTeamPints: Int)

case class Game(sport: Sport, players: List[String], result: Option[Result] = None) {

  def addPlayer(user: String): Game =  this.copy(players = players :+ user)

  def removePlayer(user: String): Game = this.copy(players = players.diff(List(user)))

}
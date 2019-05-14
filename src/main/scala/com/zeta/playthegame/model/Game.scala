package com.zeta.playthegame.model

case class User(id: String, firstName: String, lastName: String, nickName: String)

case class Team(id: String, name: String, players: List[User])

trait Sport {
  val maxPlayers: Int
}


object Sport {

  case object FootballFive extends Sport {
    override val maxPlayers: Int = 10
  }

  case object FootballEleven extends Sport {
    override val maxPlayers: Int = 22
  }

  case object Basketaball extends Sport {
    override val maxPlayers: Int = 10
  }

  case object TennisSingle extends Sport {
    override val maxPlayers: Int = 2
  }

  case object TennisDouble extends Sport {
    override val maxPlayers: Int = 4
  }

  def apply(sportName: String): Sport = sportName match {
    case "FootballFive"   => FootballFive
    case "FootballEleven" => FootballEleven
    case "Basketball"     => Basketaball
    case "TennisSingle"   => TennisSingle
  }
}

case class GameAppointment(appointmentId: String, author: User, appointmentDate: Long, createdDate: Long, game: Game) {

  def addPlayer(user: User): GameAppointment = this.copy(game = game.addPlayer(user))

  def getPlayers: List[User] = this.game.players

  def removePlayer(user: User): GameAppointment = this.copy(game = game.removePlayer(user))

  def changeAuthor(user: User): GameAppointment = this.copy(author = user)

  def setResult(result: Result): Game = this.game.copy(result = Some(result))

}

case class FinishedGame(id: String, appointmentId: String, author: User, timestamp: Long, game: Game)

case class Result(winningTeam: List[User], loserTeam: List[User], winningTeamPoints: Int, loserTeamPints: Int)

case class Game(sport: Sport, players: List[User], result: Option[Result] = None) {

  def addPlayer(user: User): Game =  this.copy(players = players :+ user)

  def removePlayer(user: User): Game = this.copy(players = players.diff(List(user)))

}
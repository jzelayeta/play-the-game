package model

import reactivemongo.bson.BSONObjectID

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
    case "footballFive"   => FootballFive
    case "footballEleven" => FootballEleven
    case "basketball"     => Basketaball
    case "tennisSingle"   => TennisSingle
  }
}

case class GameAppointment(appointmentId: String, author: User, appointmentDate: Long, createdDate: Long, gameSettings: GameSettings) {

  def addPlayer(user: User): GameAppointment = this.copy(gameSettings = gameSettings.addPlayer(user))

  def getPlayers: List[User] = this.gameSettings.currentPlayers

  def removePlayer(user: User): GameAppointment = this.copy(gameSettings = gameSettings.removePlayer(user))

  def changeAuthor(user: User): GameAppointment = this.copy(author = user)

  def markAsDone(maybeResult: Option[Result], timestamp: Long): FinishedGame = {
    FinishedGame(BSONObjectID.generate().stringify, appointmentId, author, timestamp, Game(gameSettings.sport, maybeResult))
  }
}

case class GameSettings(sport: Sport, currentPlayers: List[User]) {

  //Validate Sport max player size: https://blog.leifbattermann.de/2018/03/10/how-to-use-applicatives-for-validation-in-scala-and-save-much-work/
  def addPlayer(user: User): GameSettings = {
    this.copy(currentPlayers = currentPlayers :+ user)
  }

  def removePlayer(user: User): GameSettings = this.copy(currentPlayers = currentPlayers.diff(List(user)))
}

case class FinishedGame(id: String, appointmentId: String, author: User, timestamp: Long, game: Game)

case class Result(winningTeam: List[User], loserTeam: List[User], winningTeamPoints: Int, loserTeamPints: Int)

case class Game(sport: Sport, result: Option[Result])
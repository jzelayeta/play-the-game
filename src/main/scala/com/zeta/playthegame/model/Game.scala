package com.zeta.playthegame.model

import org.bson.types.ObjectId

case class User(id: String, firstName: String, lastName: String, nickName: String)

case class Team(id: String, name: String, players: List[User])

case class Sport(name: String, maxPlayers: Int)


object GameAppointment {
  def apply(author: User, appointmentDate: Long, createdDate: Long, game: Game): GameAppointment =
    new GameAppointment(new ObjectId().toString, author, appointmentDate, createdDate, game)
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
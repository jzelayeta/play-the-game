package com.zeta.playthegame.repository

import com.zeta.playthegame.model._
import org.mongodb.scala.bson.ObjectId

object Entities {

  case class GameAppointmentDocument(_id: ObjectId, authorId: String, appointmentDate: Long, createdDate: Long, game: GameDocument) {
    def toModel = GameAppointment(_id.toString, authorId, appointmentDate, createdDate, game.toModel)
  }

  case class GameDocument(sport: String, players: List[String], result: Option[Result]) {
    def toModel = Game(Sport(sport), players, result)
  }

}

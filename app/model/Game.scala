package model

import java.time.LocalDateTime

case class User(id: Long, firstName: String, lastName: String, nickName: String)

case class GameAppointment(appointmentId: Long, author: User)

case class FinishedGame(id: Long, appointmentId: Long, author: User, timestamp: Long)

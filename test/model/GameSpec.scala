package model

import org.joda.time.Days.{ONE, TWO, THREE}
import org.scalatest.{FlatSpecLike, Matchers}
import util.Generators

class GameSpec extends FlatSpecLike with Matchers with Generators {

  val zeta = User(randomBSONDocumentId, "julian", "zeta", "zeta")
  val palan = User(randomBSONDocumentId, "andres", "cuchimarro", "palan")
  val footballFiveGame = Game(Sport.FootballFive, List(zeta))
  val appointment = GameAppointment(randomBSONDocumentId, zeta, millisAfterDaysFromNow(TWO), millisNow, footballFiveGame)

  it should "Create an appointment and add Players to it" in {
    appointment.getPlayers.size shouldBe 1
    val appointmentUpdated = appointment.addPlayer(palan)
    appointmentUpdated.getPlayers.size shouldBe 2
  }

  it should "Create an appointment and delete Players of it" in {
    val game = Game(Sport.FootballFive, List(zeta, palan))
    val appointment = GameAppointment("1", zeta, millisAfterDaysFromNow(TWO), millisNow, game)

    val appointmentUpdated = appointment.removePlayer(palan)
    appointmentUpdated.getPlayers.size shouldBe 1
  }

  it should "Change auth appointment" in {
    appointment.author shouldBe zeta
    val appointmentUpdated = appointment.changeAuthor(palan)
    appointmentUpdated.author shouldBe palan
  }

  it should "Mark appointment as Done" in {
    val winningTeam: List[User] = (1 to 5).map(_ => randomUser).toList
    val loserTeam: List[User] = (1 to 5).map(_ => randomUser).toList
    val game = Game(Sport.FootballFive, winningTeam ++ loserTeam)

    val appointment = GameAppointment(randomBSONDocumentId, winningTeam.head, millisAfterDaysFromNow(TWO), millisNow, game)
    val result = Result(winningTeam, loserTeam, 4, 2)
    val finishedGame = appointment.setResult(result)

    finishedGame.sport shouldBe Sport.FootballFive
    finishedGame.result.map(_.winningTeamPoints shouldBe 4)
    finishedGame.result.map(_.loserTeamPints shouldBe 2)
    finishedGame.result.map(_.winningTeam shouldBe winningTeam)
    finishedGame.result.map(_.loserTeam shouldBe loserTeam)
  }

}

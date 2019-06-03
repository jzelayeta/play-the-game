package com.zeta.playthegame.model

import java.util.concurrent.TimeUnit.DAYS

import com.zeta.playthegame.model.Sport.{TennisDouble, TennisSingle}
import com.zeta.playthegame.util.Generators
import org.scalatest.{FlatSpecLike, Matchers}

class GameSpec extends FlatSpecLike with Matchers with Generators {

  val zeta = randomStringId
  val palan = randomStringId
  val footballFiveGame = Game(TennisSingle, List(zeta))
  val appointment = GameAppointment(randomStringId, zeta, millisNowPlus(2, DAYS), millisNow, footballFiveGame)

  it should "Add Players to appointment" in {
    appointment.getPlayers.size shouldBe 1
    val updatedAppointment = appointment.addPlayer(palan)
    updatedAppointment.getPlayers.size shouldBe 2
    updatedAppointment.getPlayers should contain theSameElementsAs List(zeta, palan)
  }

  it should "Delete Players of appointment" in {
    val updatedAppointment = appointment.removePlayer(zeta)
    updatedAppointment.getPlayers.size shouldBe 0
  }

  it should "Change author of appointment" in {
    val palan = randomStringId
    appointment.authorId shouldBe zeta
    val updatedAppointment = appointment.changeAuthor(palan)
    updatedAppointment.authorId shouldBe palan
  }

  it should "Verify Sport" in {
    appointment.sport shouldBe TennisSingle
  }

  it should "Change Sport" in {
    val updatedAppointment = appointment.changeSport(TennisDouble)
    updatedAppointment.sport shouldBe TennisDouble
  }

  it should "Add a result to appoinment" in {
    val result = Result(List(palan), List(zeta), 3, 2)
    val updatedAppointment = appointment.addResult(result)
    updatedAppointment.winner map {_ should contain theSameElementsAs List(palan) }
    updatedAppointment.loser  map {_  should contain theSameElementsAs List(zeta) }
  }

}
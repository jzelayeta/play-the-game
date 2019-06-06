package com.zeta.playthegame.model

import java.util.concurrent.TimeUnit.DAYS

import com.zeta.playthegame.model.Sport.{FootballFive, TennisSingle}
import com.zeta.playthegame.util.Generators
import org.scalatest.{FlatSpecLike, Matchers}

class GameSpec extends FlatSpecLike with Matchers with Generators {

  val zeta = randomStringId
  val palan = randomStringId
  val footballFive = Game(FootballFive, List(zeta))
  val appointment = Appointment(randomStringId, zeta, millisNowPlus(2, DAYS), millisNow, footballFive)

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

  it should "Change appointment date" in {
    val updated  = appointment.changeAppointmentDate(millisNowPlus(4, DAYS))
    updated.appointmentDate should be > appointment.appointmentDate
  }

  it should "Change Sport" in {
    val updated = appointment.changeSport(TennisSingle)
    updated.sport shouldBe TennisSingle
  }

  it should "Set a result" in {
    val updated = appointment.addPlayer(palan)
      .changeSport(TennisSingle)
        .addResult(Result(List(palan), List(zeta), 3, 2))

    updated.winner shouldBe 'defined
    updated.loser shouldBe  'defined

    updated.winner.get should contain theSameElementsAs List(palan)
    updated.loser.get should contain theSameElementsAs List(zeta)

  }

}

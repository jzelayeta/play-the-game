package mapping

import controllers.requests.GameAppointmentRequest
import model.Sport.FootballFive
import model.{Game, GameAppointment, Sport, User}
import org.joda.time.Days.TWO
import org.scalatest.{FlatSpecLike, Matchers}
import reactivemongo.bson.{BSONArray, BSONDocument, BSONLong, BSONObjectID}
import util.Generators

class ModelBSONMappingSpec extends FlatSpecLike with Matchers with ModelBSONMapping with Generators{

  it should "Read USer from BSON" in {
    val userBson = BSONDocument("id" -> randomBSONDocumentId, "firstName" -> "julian", "lastName" -> "zeta", "nickName" -> "zeta")
    val user = userBson.as[User]
    user.firstName shouldBe "julian"
    user.lastName shouldBe "zeta"
    user.nickName shouldBe "zeta"
  }

  it should "Read Sport from BSON" in {
    val sportBson = BSONDocument("sport" -> "FootballFive")
    sportBson.as[Sport] shouldBe FootballFive
  }

  it should "Read Game from BSON" in {
    val userBson = BSONDocument("id" -> randomBSONDocumentId, "firstName" -> "julian", "lastName" -> "zeta", "nickName" -> "zeta")
    val gameBson = BSONDocument("sport" -> "FootballFive", "players" -> BSONArray(userBson))

    val game = gameBson.as[Game]
    game.sport shouldBe FootballFive
    game.players.size shouldBe 1
    game.result shouldBe None
  }

  it should "Read GameAppointment from BSON" in {
    val userBson = BSONDocument("id" -> randomBSONDocumentId, "firstName" -> "julian", "lastName" -> "zeta", "nickName" -> "zeta")
    val gameBson = BSONDocument("sport" -> "FootballFive", "players" -> BSONArray(userBson))
    val gameAppointmentBson = BSONDocument("_id" -> BSONObjectID.generate(), "author" -> userBson, "appointmentDate" -> BSONLong(1553973666), "createdDate" -> BSONLong(1553973666), "game" -> gameBson)

    val gameAppointment = gameAppointmentBson.as[GameAppointment]

    gameAppointment.author.nickName shouldBe "zeta"
    gameAppointment.game.sport shouldBe FootballFive
    gameAppointment.game.players.size shouldBe 1

  }

}

package mapping

import model.{Game, Sport, User}
import model.Sport.{FootballFive, TennisSingle}
import org.scalatest.{FlatSpecLike, Matchers}
import play.api.libs.json.{JsObject, Json}

class ModelJSONMappingSpec extends FlatSpecLike with Matchers with ModelJSONMapping {

  it should "read Sport from JSON" in {
    val sportJson: JsObject = Json.obj("sport" -> "FootballFive")

    val sport: Sport = sportJson.as[Sport]

    sport shouldBe FootballFive
  }

  it should "write Sport to JSON" in {
    val tennisSingle: Sport = TennisSingle

    Json.toJson(tennisSingle) shouldBe Json.obj("sport" -> "TennisSingle")
  }

  it should "read User from JSON" in {
    val userJson = Json.obj("id" -> "1", "firstName" -> "julian", "lastName" -> "zeta", "nickName" -> "zeta")
    val user = userJson.as[User]

    user shouldBe User("1", "julian", "zeta", "zeta")
  }

  it should "write User to JSON" in {
    val user = User("1", "julian", "zeta", "zeta")
    val userJson = Json.obj("id" -> "1", "firstName" -> "julian", "lastName" -> "zeta", "nickName" -> "zeta")

    Json.toJson(user) shouldBe userJson
  }

  it should "read Game from JSON" in {
    val user = User("1", "julian", "zeta", "zeta")
    val game = Game(FootballFive, List(user))

    val gameJson = Json.obj("sport" -> "FootballFive", "players" -> Json.arr(user))

    gameJson.as[Game] shouldBe game
  }

  it should "read Appointment from JSON" in {

  }


}

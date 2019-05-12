package simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import util.{Generators, LoggerPerClassAware}

import scala.concurrent.duration._

class GatlingLoadTest extends Simulation with Generators with LoggerPerClassAware {
  private val baseUrl = "http://localhost:9000"
  private val contentType = "application/json"
  private val findGameAppintment = "/appointments/"
  private val deleteGameAppintment = "/appointments/"
  private val createGameAppointment = "/appointments"
  private val postHeader = Map("Content-Type" ->  """application/json""")

  def randomStr(length: Int): String = scala.util.Random.alphanumeric.filter(_.isLetter).take(length).mkString

  def buildJsonRequest(id: Int, str: String): String = {
    logger.debug(s"id is value $id")
    logger.debug(s"id str value $str")
    s"""
       |	{
       |	 "author": {
       |		"id" : "$id",
       |		"firstName": "$str",
       |		"lastName"	: "$str",
       |		"nickName"		: "$str"
       |		},
       |	 "appointmentDate": 1553973666,
       |	 "createdDate": 1553973666,
       |	 "game": {
       |	 	"sport": "FootballFive",
       |	 	"players": [{
       |		"id"        : "$id",
       |		"firstName" : "$str",
       |		"lastName"	: "$str",
       |		"nickName"  : "$str"
       |		}]
       |	 }
       |	}
    """.stripMargin
  }

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .acceptHeader("text/html,application/json,,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .contentTypeHeader(contentType)
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_0 = Map("Expect" -> "100-continue")

  val scn: ScenarioBuilder = scenario("RecordedSimulation")
    .exec(http("createAppointmentRequest")
      .post(createGameAppointment)
      .headers(postHeader)
      .body(StringBody(session => buildJsonRequest(randomId, randomStr(5)))).asJson
      .check(status.is(201))
      .check(jsonPath("$.appointmentId").saveAs("appointmentId")))
    .pause(2 seconds)
    .exec(http("findAppointmentRequest")
        .get(findGameAppintment + "${appointmentId}")
        .check(status.is(200)))
    .pause(2 seconds)
    .exec(http("deleteAppointment")
        .delete(deleteGameAppintment + "${appointmentId}")
        .check(status.is(200)))

  setUp(scn.inject(rampUsers(2000) during (20 seconds)).protocols(httpProtocol))
}
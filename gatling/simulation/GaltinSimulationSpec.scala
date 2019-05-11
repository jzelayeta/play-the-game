package simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class GatlingLoadTest extends Simulation {
  private val baseUrl = "http://localhost:9000"
  private val contentType = "application/json"
  private val endpoint = "/appointments/5c9fc2a75100007bc960e8d0"
  private val requestCount = 1000


  def buildJsonRequest(): String =
    s"""
      |{
      | "author": {
      |	"id" : "1",
      |	"firstName": "ara",
      |	"lastName"	: "fg",
      |	"nickName"		: "arita"
      |	},
      | "appointmentDate": 1553973666,
      | "createdDate": 1553973666,
      | "game": {
      | 	"sport": "FootballFive",
      | 	"players": [{
      |	"id" : "1",
      |	"firstName": "ara",
      |	"lastName"	: "fg",
      |	"nickName"		: "arita"
      |	}]
      | }
      |}
    """.stripMargin

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .contentTypeHeader(contentType)
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_0 = Map("Expect" -> "100-continue")

  val scn: ScenarioBuilder = scenario("RecordedSimulation")
    .exec(http("request_0")
      .get(endpoint)
      .check(status.is(200)))

  setUp(scn.inject(rampUsers(100) during (10 seconds)).protocols(httpProtocol))
}
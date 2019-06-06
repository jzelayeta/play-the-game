package com.zeta.playthegame.integration

import java.util.concurrent.TimeUnit.DAYS

import cats.effect.IO
import com.zeta.playthegame.model.{Game, GameAppointment}
import com.zeta.playthegame.model.Sport.FootballFive
import com.zeta.playthegame.{AppointmentRequest, AppointmentsRoutes}
import com.zeta.playthegame.repository.AppointmentsRepository
import com.zeta.playthegame.util.Generators
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.{HttpRoutes, Method, Request, Uri}
import org.scalatest.{FlatSpecLike, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global

class GameAppointmentsIT extends FlatSpecLike
  with Matchers
  with Generators
  with ServerApp
  with MongoManager {

  private val testRepository = new AppointmentsRepository(MongoTestConnection)(global)
  override val router: HttpRoutes[IO] = new AppointmentsRoutes(testRepository).routes

  override def beforeAll() = {
    dropCollections.unsafeRunSync()
    startServer
  }

  override def afterAll() = stopServer

  def request(uri: Uri, method: Method) = Request[IO](method, uri)

  def post(uri: Uri) = request(uri, Method.POST)

  def post(uri: Uri, body: Json): Request[IO] = request(uri, Method.POST).withEntity(body)

  def get(uri: Uri) = request(uri, Method.GET)

  def response(request: Request[IO]) = BlazeClientBuilder[IO](global).resource.use(_.expect[Json](request))

  def status(request: Request[IO]) = BlazeClientBuilder[IO](global).resource.use(_.status(request))

  "POST appointment" should "success when submitting a valid body" in {
    val authorId = randomStringId
    val createdDate = millisNow
    val appointmentDate = millisNowPlus(2, DAYS)
    val requestBody = AppointmentRequest(
      authorId,
      appointmentDate,
      createdDate,
      Game(FootballFive, List(randomStringId))).asJson

    val postRequest = post(baseUri / "appointments", requestBody)
    val maybeCreatedAppointment = response(postRequest).unsafeRunSync().as[GameAppointment]
    maybeCreatedAppointment shouldBe 'right
    val createdGameAppointment = maybeCreatedAppointment.right.get

    createdGameAppointment.authorId shouldBe authorId
    createdGameAppointment.appointmentDate shouldBe appointmentDate
    createdGameAppointment.createdDate shouldBe createdDate
    createdGameAppointment.game.sport shouldBe FootballFive

    val getRequest = get(baseUri / "appointments" / createdGameAppointment.appointmentId)
    val maybeRetrievedAppointment = response(getRequest).unsafeRunSync().as[GameAppointment]
    maybeRetrievedAppointment shouldBe 'right
    val retrievedGameAppointment = maybeCreatedAppointment.right.get

    retrievedGameAppointment shouldBe createdGameAppointment
  }

}

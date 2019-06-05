package com.zeta.playthegame.integration

import cats.effect.{IO, Resource}
import io.circe.Json
import org.http4s.{EntityDecoder, EntityEncoder, Message, Method, Request, Uri}
import org.http4s.circe._
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext.Implicits.global

trait RequestBuilder[A] { serverApp: ServerApp =>

//  implicit val decoder: EntityDecoder[IO, A]
//  implicit val encoder: EntityEncoder[IO, A]
//
//  private def client: (Client[IO] => IO[A]) => IO[A] = BlazeClientBuilder[IO](global).resource.use
//
//  case class RequestBuilder(uri: Uri) {
//
//    private var body: A = _
//    private var response: IO[A] = _
//
//    private def request(method: Method): Request[IO] = Request[IO](method, uri)
//
//    def post() =
//      this.copy(response = client(_.expect[A](request(Method.POST))))
//
//    def post(body: A) =
//      this.copy(response = client(_.expect[A](request(Method.POST).withEntity(body))))
//
//    def get =
//      this.copy(response = client(_.expect[A](request(Method.GET))))
//
//    def run = response.unsafeRunSync()
//
//    def withBody(body: A) = {
//      this.body = body
//      this
//    }
//
//    def withResponse(response: A) = {
//      this.response = response
//      this
//    }
//
////    val test = {
////
////      val reponse = for {
////        res <- post(appintment)
////      } yield res
////
////      response.unsafeRunSync() shouldBe
////
////    }
//
//  }
//
//
//  def request(uri: Uri): RequestBuilder = RequestBuilder(uri)
//
////  def withBody(aBody: Json) = request = request.copy(body = aBody)

}

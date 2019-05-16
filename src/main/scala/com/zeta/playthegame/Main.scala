package com.zeta.playthegame

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]) =
    PlaythegameServer.server.compile.drain.as(ExitCode.Success)
//    PlaythegameServer.stream[IO].compile.drain.as(ExitCode.Success)
}
package com.zeta.playthegame

import cats.effect.{ExitCode, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]) =
    Server.server.compile.drain.as(ExitCode.Success)
}

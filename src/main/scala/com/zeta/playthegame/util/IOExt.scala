package com.zeta.playthegame.util

import cats.effect.IO

import scala.concurrent.Future
import scala.language.implicitConversions

object IOExt {

  implicit class IOSyntax[T](ioFuture: IO[Future[T]]) {
    def toIO = IO fromFuture ioFuture
  }
}

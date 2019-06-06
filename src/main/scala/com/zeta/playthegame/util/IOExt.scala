package com.zeta.playthegame.util

import cats.effect.IO

import scala.concurrent.Future
import scala.language.implicitConversions

object IOExt {

  class IOFutureConversion[T](ioFuture: IO[Future[T]]) {
    def toIO: IO[T] = IO.fromFuture {
      ioFuture
    }
  }

  implicit def toIO[T](ioFuture: IO[Future[T]]) = new IOFutureConversion[T](ioFuture)


}

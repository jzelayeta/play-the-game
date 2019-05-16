package com.zeta.playthegame

import cats.effect.IO

trait Lala {
  val lala: Lala.Service
}

object Lala {

  case class Zeta(value: String)
  case class Palan(value: String)

  trait Service {

    def zeta: IO[Zeta] = IO(Zeta("zeta"))

    def palan: IO[Palan] = IO(Palan("palan"))

  }

  trait Live  extends Service
  object Live extends Live

}


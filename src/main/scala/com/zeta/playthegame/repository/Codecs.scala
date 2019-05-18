package com.zeta.playthegame.repository
import com.zeta.playthegame.model.{Game, GameAppointment, Sport, User}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

object Codecs {

  val codecRegistry = fromRegistries(fromProviders(classOf[GameAppointment], classOf[User], classOf[Game], classOf[Sport]), DEFAULT_CODEC_REGISTRY )


}

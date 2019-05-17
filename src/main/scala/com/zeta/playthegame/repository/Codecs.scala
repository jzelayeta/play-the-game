package com.zeta.playthegame.repository
import com.zeta.playthegame.model.GameAppointment
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

object Codecs {

  val codecRegistry = fromRegistries(fromProviders(classOf[GameAppointment]), DEFAULT_CODEC_REGISTRY )


}

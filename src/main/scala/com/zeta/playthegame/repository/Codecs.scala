package com.zeta.playthegame.repository
import com.zeta.playthegame.model.Result
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import com.zeta.playthegame.repository.Entities._

object Codecs {

  val codecRegistry = fromRegistries(fromProviders(classOf[AppointmentDocument], classOf[GameDocument], classOf[Result]), DEFAULT_CODEC_REGISTRY )


}

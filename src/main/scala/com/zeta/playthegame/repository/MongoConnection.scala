package com.zeta.playthegame.repository

import cats.effect.IO
import com.typesafe.config.ConfigFactory
import com.zeta.playthegame.repository.Codecs.codecRegistry
import com.zeta.playthegame.repository.Entities.AppointmentDocument
import org.mongodb.scala._

trait MongoConnection {
  import Codecs._

  private lazy val mongoConfig: com.typesafe.config.Config = ConfigFactory.defaultApplication().resolve().getConfig("mongodb")
  private lazy val mongoUri: String = mongoConfig.getString("uri")

  val mongoClient: IO[MongoClient] = IO {
    MongoClient(mongoUri)
  }

  val databaseName: String
  val appointmentsCollectionName: String
  val database: IO[MongoDatabase]
  val appointmentsCollection: IO[MongoCollection[AppointmentDocument]]

}

object MongoConnection extends MongoConnection {
  val databaseName = "play_the_game"
  val appointmentsCollectionName = "appointments"

  val database: IO[MongoDatabase] = mongoClient.map(_.getDatabase(databaseName).withCodecRegistry(codecRegistry))
  val appointmentsCollection: IO[MongoCollection[AppointmentDocument]] = database.map(_.getCollection(appointmentsCollectionName))
}



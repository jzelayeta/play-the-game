package com.zeta.playthegame.repository

import com.typesafe.config.ConfigFactory
import org.mongodb.scala._
import com.zeta.playthegame.repository.Entities._

object MongoConnection {

  import Codecs._

  private lazy val mongoConfig: com.typesafe.config.Config = ConfigFactory.defaultApplication().getConfig("mongodb")
  private lazy val mongoUri: String = mongoConfig.getString("uri")

  private val mongoClient: MongoClient = MongoClient(mongoUri)
  private val databaseName = "matches"
  private val appointmentsCollectionName = "appointments"
  private val usersCollectionName = "users"

  val database: MongoDatabase = mongoClient.getDatabase(databaseName).withCodecRegistry(codecRegistry)
  val appointmentsCollection: MongoCollection[GameAppointmentDocument] = database.getCollection(appointmentsCollectionName)


}



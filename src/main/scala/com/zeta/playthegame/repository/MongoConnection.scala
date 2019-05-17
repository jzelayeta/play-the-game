package com.zeta.playthegame.repository

import com.typesafe.config.ConfigFactory
import org.mongodb.scala._

object MongoConnection {

  private lazy val mongoConfig: com.typesafe.config.Config = ConfigFactory.defaultApplication().getConfig("mongodb")
  private lazy val mongoUri: String = mongoConfig.getString("uri")

  val mongoClient: MongoClient = MongoClient(mongoUri)

  val database: MongoDatabase = mongoClient.getDatabase("matches")

}



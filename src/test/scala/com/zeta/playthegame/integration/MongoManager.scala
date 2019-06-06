package com.zeta.playthegame.integration

import cats.effect.IO
import com.zeta.playthegame.repository.Codecs.codecRegistry
import com.zeta.playthegame.repository.{Entities, MongoConnection}
import com.zeta.playthegame.util.IOExt._
import org.mongodb.scala.{MongoCollection, MongoDatabase}

trait MongoManager {
  import MongoTestConnection._

  def tearDown = database.map(_.drop().head()) toIO
}

object MongoTestConnection extends MongoConnection {
  override val databaseName: String = "test"
  val appointmentsCollectionName = "appointments"
  override val database: IO[MongoDatabase] = mongoClient.map(_.getDatabase("test").withCodecRegistry(codecRegistry))
  override val appointmentsCollection: IO[MongoCollection[Entities.AppointmentDocument]] = database.map(_.getCollection(appointmentsCollectionName))
}
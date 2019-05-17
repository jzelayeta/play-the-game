package com.zeta.playthegame.repository

import cats.data.ReaderT
import com.zeta.playthegame.util.LoggerPerClassAware
import org.mongodb.scala.{Document, MongoCollection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object PersonRepository extends LoggerPerClassAware {
  import MongoConnection._
  private val appointmentsCollection: MongoCollection[Document] = database.getCollection("appointments")
  def addPerson(key: String, value: String): ReaderT[Future, PersonStore, String] = ReaderT { personStore =>
    appointmentsCollection.find().collect().subscribe((results: Seq[Document]) => logger.info(s"Found: #${results.size}"))
    for {
      person <- personStore.create(key, value)
    } yield
      person
  }

  def getPerson(key: String): ReaderT[Future, PersonStore, String] = ReaderT { personStore =>
    for {
        person  <- personStore.read(key)
    } yield person
  }
}

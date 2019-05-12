package util

import java.time.{LocalDateTime, ZoneOffset}

import model.User
import org.joda.time.Days
import reactivemongo.bson.BSONObjectID

import scala.util.Random

trait Generators extends LoggerPerClassAware {
  private val start = 0
  private val end = 1000

  def randomBSONDocumentId: String = BSONObjectID.generate().stringify

  def randomId: Int = start + Random.nextInt((end - start) + 1)

  def millisNow: Long = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli

  def millisAfterDaysFromNow(days: Days): Long = LocalDateTime.now().plusDays(days.getDays).toInstant(ZoneOffset.UTC).toEpochMilli

  def randomUser: User = User(randomBSONDocumentId, Random.nextString(5), Random.nextString(5), Random.nextString(5))

  def randomString(length: Int): String = {
    val string = scala.util.Random.alphanumeric.take(length).mkString
    logger.debug(s"random strin generated: $string")
    string
  }

}

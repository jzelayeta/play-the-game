package util

import java.time.{LocalDateTime, ZoneOffset}

import model.User
import org.joda.time.Days
import reactivemongo.bson.BSONObjectID

import scala.util.Random

trait Generators {

  def getRandomId: String = BSONObjectID.generate().stringify

  def millisNow: Long = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli

  def millisAfterDaysFromNow(days: Days): Long = LocalDateTime.now().plusDays(days.getDays).toInstant(ZoneOffset.UTC).toEpochMilli

  def randomUser: User = User(getRandomId, Random.nextString(5), Random.nextString(5), Random.nextString(5))
}

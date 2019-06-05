package com.zeta.playthegame.util

import java.time.{LocalDateTime, ZoneOffset}
import java.util.concurrent.TimeUnit
import org.bson.types.ObjectId

import scala.concurrent.duration.Duration
import scala.util.Random

trait Generators extends LoggerPerClassAware {
  private val start = 0
  private val end = 1000

  def randomId = start + Random.nextInt((end - start) + 1)

  def millisNow = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli

  def millisNowPlus(duration: Long, timeUnit: TimeUnit) = millisNow + Duration(duration, timeUnit).toMillis

  def randomString(length: Int) = scala.util.Random.alphanumeric.take(length).mkString

  def randomStringId  = new ObjectId().toString

}

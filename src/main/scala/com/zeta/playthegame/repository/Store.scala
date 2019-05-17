package com.zeta.playthegame.repository

import java.util.concurrent.ConcurrentHashMap

import com.zeta.playthegame.util.LoggerPerClassAware

import scala.concurrent.Future


trait Store[K, V] extends LoggerPerClassAware {
  val s = scala.collection.mutable.Map[K, V]()

  def create(k: K, v: V): Future[V]

  def read(k: K): Future[V]

  def update(k: K, v: V): Future[V]

//  def delete(k: K): Future[V]
}

class PersonStore extends Store[String, String] {
  def create(k: String, v: String): Future[String] = {
    s += (k -> v)
    logger.info(s"inserted key $k with value $v")
    Future.successful(s(v))
  }

  def read(k: String): Future[String] = Future.successful(s(k))

  def update(k: String, v: String): Future[String] = create(k, v)
//
//  def delete(k: String): Future[Option[String]] = {
//    Future.successful(s.remove(k))
//  }
}
package com.zeta.playthegame.repository

import java.util.concurrent.ConcurrentHashMap

import com.zeta.playthegame.model.GameAppointment

import scala.concurrent.Future


trait Store[K, V] {
  val s = new ConcurrentHashMap[K, V]()

  def create(k: K, v: V): Future[V]

  def read(k: K): Future[V]

  def update(k: K, v: V): Future[V]

  def delete(k: K): Future[V]
}

class GameStore extends Store[String, GameAppointment] {
  def create(k: String, v: GameAppointment): Future[GameAppointment] = Future.successful(s.put(k, v))

  def read(k: String): Future[GameAppointment] = Future.successful(s.get(k))

  def update(k: String, v: GameAppointment): Future[GameAppointment] = create(k, v)

  def delete(k: String): Future[GameAppointment] = Future.successful(s.remove(k))
}
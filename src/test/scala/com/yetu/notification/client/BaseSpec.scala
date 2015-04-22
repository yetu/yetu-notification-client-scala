package com.yetu.notification.client

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import org.scalatest.{MustMatchers, BeforeAndAfterAll, Matchers, WordSpecLike}

abstract class BaseSpec
  extends TestKit(ActorSystem("NotificationClientTestSystem"))
  with DefaultTimeout
  with ImplicitSender
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll {

  override def afterAll {
    shutdown()
  }

}

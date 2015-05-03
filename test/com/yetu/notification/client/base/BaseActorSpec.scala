package com.yetu.notification.client.base

import akka.actor.ActorSystem
import akka.testkit.TestKit
import akka.util.Timeout

import com.yetu.notification.client.util.TestLogger
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.{WsScalaTestClient, OneAppPerSuite, PlaySpec}
import play.api.libs.concurrent.Akka
import play.api.test.FakeApplication
import scala.concurrent.duration._

class BaseActorSpec(_system: ActorSystem) extends TestKit(_system)
with WordSpecLike
with OneAppPerSuite
with TestLogger
with ScalaFutures
with BeforeAndAfter
with MustMatchers
with OptionValues
with WsScalaTestClient
with BeforeAndAfterAll {
  implicit override lazy val app: FakeApplication = FakeApplication(additionalConfiguration = Map(
    "logger.application" -> "DEBUG"
  ))

  implicit val timeout = Timeout(5 seconds)
}


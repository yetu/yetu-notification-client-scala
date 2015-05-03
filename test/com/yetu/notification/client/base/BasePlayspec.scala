package com.yetu.notification.client.base

import com.yetu.notification.client.util.TestLogger
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.test.FakeApplication

class BasePlaySpec extends PlaySpec with WordSpecLike
with OneAppPerSuite
with TestLogger
with ScalaFutures
with BeforeAndAfter
with MustMatchers
with OptionValues
with BeforeAndAfterAll {
  implicit override lazy val app: FakeApplication = FakeApplication(additionalConfiguration = Map(
    "logger.application" -> "DEBUG"
  ))
}

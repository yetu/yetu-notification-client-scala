package com.yetu.notification.client.base

import akka.testkit.TestKit
import com.yetu.notification.client.util.TestLogger
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfter}
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.OneAppPerSuite
import org.scalatestplus.play.PlaySpec
import play.api.test.FakeApplication


class BaseSpec extends PlaySpec with OneAppPerSuite with TestLogger with ScalaFutures with BeforeAndAfter with BeforeAndAfterAll {
  implicit override lazy val app: FakeApplication = FakeApplication(additionalConfiguration = Map(
    "logger.application" -> "DEBUG"
  ))

}


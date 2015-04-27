package com.yetu.notification.client

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.{AsyncAssertions, Eventually, ScalaFutures}
import org.scalatest.time.SpanSugar._
import org.scalatest.time.{Millis, Seconds, Span}
import scala.concurrent.ExecutionContext.Implicits.global

class NotificationManagerTest extends BaseSpec with ScalaFutures with Eventually with MockFactory {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(50, Seconds), interval = Span(10, Millis))

  "Notification manager" must {
    "send message to message queue" in {
      val x = new NotificationManager()
      whenReady(x.send(TestVariables.correctMessage)) { res =>
        res.status mustEqual 202
      }
    }

    "send and receive message at default mq" in {
      val x = new NotificationManager()
      val m = mockFunction[String, String]
      x.subscribe("some-user-strange-uuid.*.logout", m) map {
        case e =>
          val w = new AsyncAssertions.Waiter
          x.send(TestVariables.correctMessage)
          m.expects(*) atLeastOnce() onCall ((s:String) => {
            println("On Call called")
            w.dismiss()
            s
          })
          w.await(timeout(10 seconds))
      }

    }
  }
}

package com.yetu.notification.client

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

class NotificationManagerTest extends BaseSpec with ScalaFutures {

  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(50, Seconds), interval = Span(50, Millis))

  "Notification manager" must {
    "send message to message queue" in {
      val x = new NotificationManager()
      whenReady(x.send(TestVariables.correctMessage)) { res =>
        res.status mustEqual 202
      }
    }



//    {
//      "scope": "bar",
//      "userUUID": "some-user-strange-uuid",
//      "clientId": "com.yetu.netatmo.thermostat",
//      "iat": 1423573831,
//      "exp": 1734613831,
//      "aud": "events",
//      "iss": "https://auth.yetudev.com",
//      "sub": "subject.with.dots"
//    }

    "send and receive message at default mq" ignore {
      val x = new NotificationManager()
      x.subscribe("some-user-strange-uuid.*.logout",(s:String) => {
        s mustNot be(null)
      })
      whenReady(x.send(TestVariables.correctMessage)) { res =>
      }

    }

  }
}

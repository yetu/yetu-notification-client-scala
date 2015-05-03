package com.yetu.notification.client.mq

import akka.testkit.TestProbe
import com.yetu.notification.client.NotificationManager
import com.yetu.notification.client.base.BaseSpec
import com.yetu.notification.client.messages.MsgDeliver
import com.yetu.notification.client.model.RabbitMessage
import play.api.libs.concurrent.Akka

import scala.concurrent.ExecutionContext.Implicits.global


class SendReceiveTest extends BaseSpec {

  implicit val system = Akka.system

  "Notification client " must {
    "send and receive messages with actor interface" in {
      val testProbe = TestProbe()
      NotificationManager.bindConsumer("#", testProbe.ref) map {
        case _ =>
          NotificationManager.publisherActor ! RabbitMessage("asdf.adsf.adf","testValue")
      }
      testProbe.expectMsg(MsgDeliver("testValue"))
    }
  }
}

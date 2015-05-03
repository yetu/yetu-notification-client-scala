
package com.yetu.notification.client.it

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import com.yetu.notification.client.NotificationManager
import com.yetu.notification.client.base.BaseActorSpec
import com.yetu.notification.client.messages.MsgDeliver
import com.yetu.notification.client.model.RabbitMessage

import scala.concurrent.ExecutionContext.Implicits.global

class MqSendReceiveTest(_system: ActorSystem) extends BaseActorSpec(_system) {

  def this() = this(ActorSystem("test"))

  val senderTopic: String = "userId.clientId.event"
  val samplePayload: String = "testValue"

  "Notification client" must {

    "send and receive messages with actor interface" in {
      val testProbe = TestProbe()
      NotificationManager.bindConsumer("#", testProbe.ref) map {
        case _ =>
          NotificationManager.publisherActor ! RabbitMessage(senderTopic, samplePayload)
      }
      testProbe.expectMsg(MsgDeliver(samplePayload))
    }

    "send and receive messages with actor interface by topic" in {
      val testProbe = TestProbe()
      NotificationManager.bindConsumer("*.*.event", testProbe.ref) map {
        case _ =>
          NotificationManager.publisherActor ! RabbitMessage(senderTopic, samplePayload)
      }
      testProbe.expectMsg(MsgDeliver(samplePayload))
    }
  }
}

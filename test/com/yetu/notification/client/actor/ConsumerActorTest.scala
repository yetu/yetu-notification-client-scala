package com.yetu.notification.client.actor

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.{TestActorRef, TestProbe}
import com.github.sstone.amqp.Amqp.{Ack, Delivery}
import com.rabbitmq.client.Envelope
import com.yetu.notification.client.base.BaseActorSpec
import com.yetu.notification.client.messages.{UnknownMsg, MsgDeliver}

class ConsumerActorTest(_system: ActorSystem) extends BaseActorSpec(_system) {

  def this() = this(ActorSystem("test"))

  val testMessage = "testMssage"
  val testEnvelope = new Envelope(42, false, "test", "test")

  val deliveryMsg = Delivery(body = testMessage.getBytes,
    consumerTag = "",
    envelope = testEnvelope,
    properties = null)

  val wrongDeliveryMsg = Delivery(body = null,
    consumerTag = "",
    envelope = testEnvelope,
    properties = null)

  "Mq consumer actor" must {

    "accept Delivery messages" in {
      val consumerActorRef = TestActorRef(ConsumerActor.props(TestProbe().ref))
      whenReady(consumerActorRef ? deliveryMsg) { res =>
        res mustBe an[Ack]
      }
    }

    "acknowledge messages with info about delivery tag" in {
      val consumerActorRef = TestActorRef(ConsumerActor.props(TestProbe().ref))
      whenReady(consumerActorRef ? deliveryMsg) { ack =>
        ack mustEqual Ack(testEnvelope.getDeliveryTag)
      }
    }

    "tell to message listener actor about body content" in {
      val probe: TestProbe = TestProbe()
      val consumerActorRef = TestActorRef(ConsumerActor.props(probe.ref))
      consumerActorRef ! deliveryMsg
      probe.expectMsg(MsgDeliver(testMessage))
    }

    "do not send anything to message listener on null body" in {
      val probe: TestProbe = TestProbe()
      val consumerActorRef = TestActorRef(ConsumerActor.props(probe.ref))
      whenReady(consumerActorRef ? wrongDeliveryMsg){ res =>
        res mustEqual UnknownMsg(wrongDeliveryMsg)
      }
    }
  }
}

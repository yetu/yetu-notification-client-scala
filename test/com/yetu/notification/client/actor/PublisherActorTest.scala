package com.yetu.notification.client.actor

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestProbe}
import com.github.sstone.amqp.Amqp._
import com.rabbitmq.client.AMQP.BasicProperties
import com.yetu.notification.client.Config
import com.yetu.notification.client.base.BaseActorSpec
import com.yetu.notification.client.model.RabbitMessage
import akka.pattern.ask


class PublisherActorTest(_system: ActorSystem) extends BaseActorSpec(_system) {

  def this() = this(ActorSystem("test"))

  val producerProbe = TestProbe()
  var publisherActor = TestActorRef(PublisherActor.props(
    producer = producerProbe.ref,
    exchangeParameters = Config.exchangeParams)
  )

  override def beforeAll() {
    producerProbe.expectMsg(ConfirmSelect)
    producerProbe.expectMsg(AddReturnListener(publisherActor))
    producerProbe.expectMsg(AddConfirmListener(publisherActor))
    producerProbe.expectMsg(DeclareExchange(Config.exchangeParams))
  }

  "Mq publisher actor" must {

    "init mq and exchange on pre start" in {
      publisherActor.start()
    }

    "accept RabbitMessage and publish to mq" in {
      val message: RabbitMessage = RabbitMessage(key = "test.topic.event", payload = "testMessage")
      publisherActor ! message
      producerProbe.expectMsgClass(classOf[Publish])
      producerProbe.expectMsg(WaitForConfirms(None))
    }

    "process Returned message as echo" in {
      whenReady(publisherActor ? ReturnedMessage(42,"", Config.RABBITMQ_EXCHANGE_NAME,  "test.topic.event", null, "test".getBytes)) {
        msg => msg mustBe an[ReturnedMessage]
      }
    }
  }
}

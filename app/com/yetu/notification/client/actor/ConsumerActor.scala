package com.yetu.notification.client.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.github.sstone.amqp.Amqp.{Ack, Delivery}
import com.yetu.notification.client.messages.{UnknownMsg, MsgDeliver}
import play.api.Logger

class ConsumerActor(messageListener: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {
    case Delivery(consumerTag, envelope, properties, body) if body != null =>
      Logger.debug(s"Delivery event happened with info envelope: $envelope with")
      
      val msg: String = new Predef.String(body)
      messageListener ! MsgDeliver(msg)

      // for now Ack happens immediately, but MsgProcessed can be used
      sender ! Ack(envelope.getDeliveryTag)
    case other =>
      log.warning(s"Got other message $other")
      sender ! UnknownMsg(other)
  }
}


object ConsumerActor {
  def props(messageListener: ActorRef): Props = Props(new ConsumerActor(messageListener))
}

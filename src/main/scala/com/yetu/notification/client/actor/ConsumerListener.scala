package com.yetu.notification.client.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.github.sstone.amqp.Amqp._

class ConsumerListener(action: (String) => ()) extends Actor with ActorLogging {

  override def receive: Receive = {
    case Delivery(consumerTag, envelope, properties, body) => {
      log.info("got a message: " + new String(body))
      action.apply(new String(body))
      sender ! Ack(envelope.getDeliveryTag)
    }
    case other => log.warning(s"Got other message $other")
  }
}

object ConsumerListener {
  def props(action: (String) => ()): Props = Props(new ConsumerListener(action))
}

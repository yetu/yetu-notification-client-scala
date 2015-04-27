package com.yetu.notification.client.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.github.sstone.amqp.Amqp._

class ConsumerListener(action: (String) => String) extends Actor with ActorLogging {

  override def receive: Receive = {
    case Delivery(consumerTag, envelope, properties, body) =>
      println("On on consume called")
      val msg: String = new Predef.String(body)
      action(msg)
      sender ! Ack(envelope.getDeliveryTag)
    case other => log.warning(s"Got other message $other")
  }
}

object ConsumerListener {
  def props(action: (String) => String): Props = Props(new ConsumerListener(action))
}

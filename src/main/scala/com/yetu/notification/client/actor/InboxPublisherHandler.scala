package com.yetu.notification.client.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.yetu.notification.client.model.ClientMessage
import play.api.libs.ws.{WSResponse, WS}

class InboxPublisherHandler extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg: ClientMessage =>
      val s: String = "http://inbox.yetudev.com/publish"
      WS.url(s).post(msg).map {
        r:WSResponse => sender ! r
      }
    case other => log.info(s"Unexpected Message: $other")
  }
}

object InboxPublisherHandler {
  def props = Props(new InboxPublisherHandler)
}

package com.yetu.notification.client.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.yetu.notification.client.Config
import com.yetu.notification.client.model.ClientMessage
import play.api.libs.json.Json
import play.api.libs.ws.{WS, WSResponse}
import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global


class InboxPublisherHandler extends Actor with ActorLogging {

  def receive: Receive = {
    case msg: ClientMessage =>
      val selfSender = sender()
      // WS has it's own sender
      WS.url(Config.INBOX_PUBLISH_URL).post(Json.toJson(msg)).map {
        r: WSResponse => selfSender ! r
      }
    case other => log.info(s"Unexpected Message: $other")
  }
}

object InboxPublisherHandler {
  def props = Props(new InboxPublisherHandler)
}

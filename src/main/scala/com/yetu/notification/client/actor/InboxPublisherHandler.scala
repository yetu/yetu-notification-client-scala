package com.yetu.notification.client.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.yetu.notification.client.Config
import com.yetu.notification.client.model.ClientMessage
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse

import scala.concurrent.ExecutionContext.Implicits.global

object dsfa {
  val builder = new com.ning.http.client.AsyncHttpClientConfig.Builder()
  val client = new play.api.libs.ws.ning.NingWSClient(builder.build())
}

class InboxPublisherHandler extends Actor with ActorLogging {




  def receive: Receive = {
    case msg: ClientMessage =>
      val selfSender = sender()
      // WS has it's own sender
      dsfa.client.url(Config.INBOX_PUBLISH_URL).post(Json.toJson(msg)).map {
        r: WSResponse => selfSender ! r
      }
    case other => log.info(s"Unexpected Message: $other")
  }
}

object InboxPublisherHandler {
  def props = Props(new InboxPublisherHandler)
}

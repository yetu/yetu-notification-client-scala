package com.yetu.notification.client.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.yetu.notification.client.model.ClientMessage
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse

import scala.concurrent.ExecutionContext.Implicits.global

class InboxPublisherHandler extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg: ClientMessage =>
      val selfSender = sender()
      val s: String = "http://inbox.yetudev.com/publish"
      val builder = new com.ning.http.client.AsyncHttpClientConfig.Builder()
      val client = new play.api.libs.ws.ning.NingWSClient(builder.build())

      // WS has it's own sender
      client.url(s).post(Json.toJson(msg)).map {
        r: WSResponse => selfSender ! r
      }
    case other => log.info(s"Unexpected Message: $other")
  }
}

object InboxPublisherHandler {
  def props = Props(new InboxPublisherHandler)
}

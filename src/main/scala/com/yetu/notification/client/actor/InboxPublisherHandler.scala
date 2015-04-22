package com.yetu.notification.client.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.yetu.notification.client.model.ClientMessage
import play.api.libs.json.Json
import play.api.libs.ws.{WSResponse, WS}
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

class InboxPublisherHandler extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg: ClientMessage =>
      val s: String = "http://inbox.yetudev.com/publish"
      WS.url(s).post(Json.toJson(msg)).map {
        r:WSResponse => sender ! r
      }
    case other => log.info(s"Unexpected Message: $other")
  }
}

object InboxPublisherHandler {
  def props = Props(new InboxPublisherHandler)
}

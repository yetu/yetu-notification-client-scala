package com.yetu.notification.client.model

import play.api.libs.json.{Format, Json}


case class RabbitMessage(key: String = "*", payload: String)

object RabbitMessage {
  implicit val messageJsonFormat: Format[ClientMessage] = Json.format[ClientMessage]
}
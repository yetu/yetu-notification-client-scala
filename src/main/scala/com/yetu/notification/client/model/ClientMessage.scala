package com.yetu.notification.client.model

import play.api.libs.json.Json


case class ClientMessage(token: String, payload: Payload)

object ClientMessage {
  implicit val messageJsonFormat = Json.format[ClientMessage]
}A
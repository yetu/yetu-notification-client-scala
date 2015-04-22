package com.yetu.notification.client.model


case class ClientMessage(token: String, payload: Payload)

object ClientMessage {
  implicit val messageJsonFormat = Json.format[ClientMessage]
}
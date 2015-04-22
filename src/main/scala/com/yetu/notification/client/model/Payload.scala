package com.yetu.notification.client.model

case class Payload(event : String, data: JsValue)

object Payload {
  implicit val payloadJsonFormat = Json.format[Payload]
}
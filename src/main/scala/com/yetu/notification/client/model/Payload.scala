package com.yetu.notification.client.model

import play.api.libs.json.{Json, JsValue}

case class Payload(event : String, data: JsValue)

object Payload {
  implicit val payloadJsonFormat = Json.format[Payload]
}
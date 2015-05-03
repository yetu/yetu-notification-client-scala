package com.yetu.notification.client.model

import com.yetu.oauth2resource.model.ValidationResponse
import play.api.libs.json.{Format, Json}


case class RabbitMessage(key: String = "*", payload: String)

object RabbitMessage {
  implicit val messageJsonFormat: Format[ClientMessage] = Json.format[ClientMessage]

  def convertToRabbitMessage(validationResponse: ValidationResponse, clientMessage: ClientMessage): Option[RabbitMessage] = {
    def dots2Dashes(s: String) = {
      s.replaceAll("\\.", "-")
    }
    val payload = clientMessage.payload
    val payloadJsonString = Json.toJson(payload).toString()
    for {
      userId <- validationResponse.userUUID
      clientId <- validationResponse.clientId
    // target and feature my be here in future
    } yield RabbitMessage(key = s"${dots2Dashes(userId)}.${dots2Dashes(clientId)}.${dots2Dashes(payload.event)}", payload = payloadJsonString)
  }
}
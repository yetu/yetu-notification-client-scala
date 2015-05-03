package com.yetu.notification.client.model

import com.yetu.notification.client.base.BaseSpec
import com.yetu.oauth2resource.model.ValidationResponse
import play.api.libs.json.{JsString, JsObject, Json, Format}
import com.yetu.notification.client.util.TestVariables._

class RabbitMessageTest extends BaseSpec {

  "RabbitMessage" must {
    "have json format value on companion object" in {
      RabbitMessage.messageJsonFormat mustBe a[Format[RabbitMessage]]
    }

    "know compose RabbitMessage from validation responce and client message" in {
      val validateResponce = ValidationResponse(userUUID = Some("someUserUdid"), clientId = Some("cleintid"))
      val rabbitMessage = RabbitMessage.convertToRabbitMessage(validateResponce, correctMessage)
      rabbitMessage mustBe Some(RabbitMessage(s"someUserUdid.${validateResponce.clientId.get}.${correctMessage.payload.event.replaceAll("\\.","-")}",
        payload = Json.toJson(correctMessage.payload).toString()))
    }

    "compose some of correct RabbitMessage with client message with dotted subjectId" in {
      val validateResponce = ValidationResponse(userUUID = Some("someUserUdid"), clientId = Some("cleint.id.with.dots"))
      val cleintMessage = ClientMessage(correctToken,
        Payload("event",
          JsObject(
            Seq("playlistId" -> JsString("1234567"))
          )
        )
      )

      val rabbitMessage = RabbitMessage.convertToRabbitMessage(validateResponce, cleintMessage)
      rabbitMessage mustBe Some(RabbitMessage(s"someUserUdid.${validateResponce.clientId.get.replaceAll("\\.","-")}.${cleintMessage.payload.event.replaceAll("\\.","-")}",
        payload = Json.toJson(cleintMessage.payload).toString()))
    }

    "compose None in convertToRabbitMessage when validation responce doesn't have userUUID property" in {
      val validateResponce = ValidationResponse(userUUID = None,  clientId = Some("cleint.id.with.dots"))
      val rabbitMessage = RabbitMessage.convertToRabbitMessage(validateResponce, correctMessage)
      rabbitMessage mustBe None
    }


    "compose None in convertToRabbitMessage when validation responce doesn't have clientId property" in {
      val validateResponce = ValidationResponse(userUUID = Some("someUserUdid"),  clientId = None)
      val rabbitMessage = RabbitMessage.convertToRabbitMessage(validateResponce, correctMessage)
      rabbitMessage mustBe None
    }
  }
}
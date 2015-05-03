package com.yetu.notification.client.util

import com.typesafe.config.ConfigFactory
import com.yetu.notification.client.model.{Payload, ClientMessage}
import play.api.libs.json.{Json, JsValue, JsString, JsObject}
import play.api.test.FakeRequest
import play.api.test.Helpers._

object TestVariables {

  val conf = ConfigFactory.load()
  val wrongAudienceToken = conf.getString("oauth.test.invalid.token.aud")
  val correctToken = conf.getString("oauth.test.token")
  val wrongToken = "wrong_token"
  val correctMessage = ClientMessage(correctToken,
    Payload("someEvent",
      JsObject(
        Seq("playlistId" -> JsString("1234567"))
      )
    )
  )

  def message2Json(msg : ClientMessage, method: String = GET, path: String = "/") ={
    val json: JsValue = Json.toJson(msg)
    println(json.toString())
    FakeRequest(method, path).withJsonBody(json)
  }
}

package com.yetu.notification.client

import com.typesafe.config.ConfigFactory
import com.yetu.notification.client.model.{ClientMessage, Payload}
import play.api.libs.json.{JsObject, JsString}

object TestVariables {

  val conf = ConfigFactory.load()
  val wrongAudienceToken = conf.getString("oauth.test.invalid.token.aud")
  val correctToken = conf.getString("oauth.test.token")
  val wrongToken = "wrong_token"
  val correctMessage = ClientMessage(correctToken,
    Payload("logout",
      JsObject(
        Seq("playlistId" -> JsString("1234567"))
      )
    )
  )

}

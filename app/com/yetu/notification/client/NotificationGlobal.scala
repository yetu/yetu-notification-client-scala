package com.yetu.notification.client

import play.api.{Application, GlobalSettings}

object NotificationGlobal extends NotificationGlobal

trait NotificationGlobal extends GlobalSettings {

  override def onStart(app: Application): Unit = {
    NotificationManager.init()
  }
}
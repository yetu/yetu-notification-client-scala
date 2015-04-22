package com.yetu.notification.client

import com.rabbitmq.client.ConnectionFactory
import com.typesafe.config.ConfigFactory

object Config {
  val configObject = ConfigFactory.load()
  val RABBITMQ_HOST = configObject.getString("rabbitmq.host")
  val RABBITMQ_EXCHANGE = configObject.getString("rabbitmq.exchange")
  val RABBITMQ_VIRTUALHOST = configObject.getString("rabbitmq.virtualhost")
  val RABBITMQ_PASSWORD = configObject.getString("rabbitmq.password")
  val RABBITMQ_USERNAME = configObject.getString("rabbitmq.username")
  val RABBITMQ_USE_SSL = configObject.getBoolean("rabbitmq.useSsl")
  val RABBITMQ_HEARTBEAT = configObject.getInt("rabbitmq.heartbeat")


  def rabbitMQConnectionSettings(): ConnectionFactory = {
    val factory = new ConnectionFactory()
    factory.setHost(Config.RABBITMQ_HOST)
    factory.setVirtualHost(Config.RABBITMQ_VIRTUALHOST)
    factory.setUsername(Config.RABBITMQ_USERNAME)
    factory.setPassword(Config.RABBITMQ_PASSWORD)
    factory.setRequestedHeartbeat(Config.RABBITMQ_HEARTBEAT)

    if (RABBITMQ_USE_SSL) {
      factory.useSslProtocol()
    }
    factory
  }
}
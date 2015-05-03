package com.yetu.notification.client

import com.github.sstone.amqp.Amqp.{QueueParameters, ExchangeParameters}
import com.rabbitmq.client.ConnectionFactory
import com.typesafe.config.ConfigFactory

object Config {
  val configObject = ConfigFactory.load()
  val RABBITMQ_HOST = configObject.getString("rabbitmq.host")

  val RABBITMQ_EXCHANGE_NAME = configObject.getString("rabbitmq.exchange.name")
  val RABBITMQ_EXCHANGE_TYPE = configObject.getString("rabbitmq.exchange.type")
  val RABBITMQ_EXCHANGE_PASSIVE = configObject.getBoolean("rabbitmq.exchange.passive")
  val RABBITMQ_EXCHANGE_DURABLE = configObject.getBoolean("rabbitmq.exchange.durable")
  val RABBITMQ_EXCHANGE_AUTODELETE = configObject.getBoolean("rabbitmq.exchange.autodelete")

  // queue
  val RABBIT_CONSUME_QUEUE_NAME = configObject.getString("rabbitmq.consumeQueue.name")
  val RABBIT_CONSUME_QUEUE_PASSIVE = configObject.getBoolean("rabbitmq.consumeQueue.passive")
  val RABBIT_CONSUME_QUEUE_DURABLE = configObject.getBoolean("rabbitmq.consumeQueue.durable")
  val RABBIT_CONSUME_QUEUE_EXCLUSIVE = configObject.getBoolean("rabbitmq.consumeQueue.exclusive")
  val RABBIT_CONSUME_QUEUE_AUTODELETE = configObject.getBoolean("rabbitmq.consumeQueue.autodelete")

  val RABBITMQ_VIRTUALHOST = configObject.getString("rabbitmq.virtualhost")
  val RABBITMQ_PASSWORD = configObject.getString("rabbitmq.password")
  val RABBITMQ_USERNAME = configObject.getString("rabbitmq.username")
  val RABBITMQ_USE_SSL = configObject.getBoolean("rabbitmq.useSsl")
  val RABBITMQ_HEARTBEAT = configObject.getInt("rabbitmq.heartbeat")
  val INBOX_PUBLISH_URL = configObject.getString("yetu.inboxPublishUrl")

  val exchangeParams = ExchangeParameters(
    name = Config.RABBITMQ_EXCHANGE_NAME,
    exchangeType = Config.RABBITMQ_EXCHANGE_TYPE,
    passive = Config.RABBITMQ_EXCHANGE_PASSIVE,
    durable = Config.RABBITMQ_EXCHANGE_DURABLE,
    autodelete = Config.RABBITMQ_EXCHANGE_AUTODELETE
  )

  val queueParams = QueueParameters(
    name = Config.RABBIT_CONSUME_QUEUE_NAME,
    passive = Config.RABBIT_CONSUME_QUEUE_PASSIVE,
    durable = Config.RABBIT_CONSUME_QUEUE_DURABLE,
    exclusive = Config.RABBIT_CONSUME_QUEUE_EXCLUSIVE,
    autodelete = Config.RABBIT_CONSUME_QUEUE_AUTODELETE
  )

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
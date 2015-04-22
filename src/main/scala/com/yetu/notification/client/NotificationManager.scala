package com.yetu.notification.client

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import com.github.sstone.amqp.Amqp._
import com.github.sstone.amqp.{Amqp, ConnectionOwner, Consumer}
import com.yetu.notification.client.actor.{ConsumerListener, InboxPublisherHandler}
import com.yetu.notification.client.model.ClientMessage
import play.api.libs.ws.WSResponse

import scala.concurrent.Future
import scala.concurrent.duration._

class NotificationManager(implicit system: ActorSystem) {

  implicit val timeout = Timeout(5 seconds) // timeout for ask pattern

  // TODO make exchange option configurable
  lazy val exchange = ExchangeParameters(
    name = Config.RABBITMQ_EXCHANGE,
    exchangeType = "topic",
    passive = false,
    durable = true,
    autodelete = false
  )

  //Create the connection to MQ
  val connection = system.actorOf(
    ConnectionOwner.props(Config.rabbitMQConnectionSettings(), 1 second)
  )

  val publisherHandler = initPublisher()

  def send(msg: ClientMessage): Future[WSResponse] = {
    (publisherHandler ? msg).mapTo[WSResponse]
  }

  // *.com-yetu-oauth2provider.logout
  def subscribe(topic: String, action: (String) => Unit): Unit = {
    initConsumerHandler(topic, action)
  }

  /**
   * Leave this stuff in case if you need to switch from inbox service to direct mq connecton
   */
  private def initPublisher() = {

//    //Create channel for producer
//    val producer = ConnectionOwner.createChildActor(connection, ChannelOwner.props())
//    //Make sure connection is established between client and the MQ
//    Amqp.waitForConnection(system, producer).await()
//    //Add publish handler to existing channel

    system.actorOf(InboxPublisherHandler.props)
  }

  private def initConsumerHandler(topic: String, action: (String) => Unit) = {
    //Create ConsumerListener, which consumes the messages
    val consumerListener = system.actorOf(ConsumerListener.props(action))

    //Assign ConsumerListener to the consumer channel
    val consumer = ConnectionOwner.createChildActor(
      connection,
      Consumer.props(consumerListener,
        channelParams = None,
        autoack = false)
    )

    //Make sure connection is established between client and the MQ
    Amqp.waitForConnection(system, consumer).await()

    val queue_name = "" // TODO make configurable
    //Add queue params to the consumer
    val queueParams = QueueParameters(
      queue_name,
      passive = false,
      durable = true,
      exclusive = false,
      autodelete = true
    )

    consumer ! DeclareQueue(queueParams)
    consumer ! QueueBind(
      queue = queue_name,
      exchange = Config.RABBITMQ_EXCHANGE,
      routing_key = topic
    )

    consumer ! AddQueue(QueueParameters(name = queue_name, passive = false, durable = true))
    consumerListener
  }
}

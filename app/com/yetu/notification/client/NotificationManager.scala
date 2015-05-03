package com.yetu.notification.client

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.github.sstone.amqp.Amqp._
import com.github.sstone.amqp.{Amqp, ChannelOwner, ConnectionOwner, Consumer}
import com.yetu.notification.client.actor.{ConsumerActor, PublisherHandler}
import play.api.libs.concurrent.Akka
import play.api.Logger
import play.api.Play.current

import scala.concurrent.Future
import scala.concurrent.duration._

object NotificationManager {


  implicit val timeout = Timeout(30 seconds) // timeout for ask pattern

  private val system = Akka.system

  //Create the connection to MQ
  val connectionActor = system.actorOf(
    ConnectionOwner.props(Config.rabbitMQConnectionSettings(), 1 second)
  )

  val publisherActor = {
    //Create channel for producer
    val producer = ConnectionOwner.createChildActor(connectionActor, ChannelOwner.props())
    //Make sure connection is established between client and the MQ
    Amqp.waitForConnection(system, producer).await()
    system.actorOf(PublisherHandler.props(producer, Config.exchangeParams))
  }


  def init() = Logger.info("Init vals initialization")

  def bindConsumer(topic: String, listenerActor: ActorRef) = {
    val consumerListener = system.actorOf(ConsumerActor.props(listenerActor))

    //Assign ConsumerListener to the consumer channel
    val consumer: ActorRef = ConnectionOwner.createChildActor(
      connectionActor,
      Consumer.props(
        consumerListener,
        channelParams = None,
        autoack = false
      )
    )

    //Make sure connection is established between client and the MQ
    Amqp.waitForConnection(Akka.system, consumer).await()

    consumer ! AddQueue(Config.queueParams)

    consumer ? QueueBind(
      queue = Config.RABBIT_CONSUME_QUEUE_NAME,
      exchange = Config.RABBITMQ_EXCHANGE_NAME,
      routing_key = topic
    )
  }

}
package com.yetu.notification.client.actor

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.util.Timeout
import com.github.sstone.amqp.Amqp._
import com.yetu.notification.client.Config
import com.yetu.notification.client.model.RabbitMessage

import scala.Error

/**
 * Use this actor in case if you want to communicate with MQ directly
 */
class PublisherActor(producer: ActorRef, exchangeParameters: ExchangeParameters) extends Actor with ActorLogging {
  //Do not remove the context from the class
  //required for the akka

  override def preStart() = {
    producer ! ConfirmSelect
    producer ! AddReturnListener(self)
    producer ! AddConfirmListener(self)
    producer ! DeclareExchange(exchangeParameters)
  }

  def receive = {
    case message: RabbitMessage =>
      implicit val timeout = Timeout(5, TimeUnit.SECONDS)

      producer ! Publish(Config.RABBITMQ_EXCHANGE_NAME, message.key, message.payload.toString.getBytes)
      producer ! WaitForConfirms(None)
    case message: HandleAck => log.debug(s"HandleAck : $message")
    case message: Ok => log.debug(s"Ok : $message")
    case message: Error => log.error(s"Error : $message")
    case message: ReturnedMessage =>
      log.error(s"ReturnedMessage : $message")
      sender ! message
    case message => log.warning(s"Unexpected message $message")
  }

}

object PublisherActor {
  def props(producer: ActorRef, exchangeParameters: ExchangeParameters): Props = Props(new PublisherActor(producer, exchangeParameters))
}
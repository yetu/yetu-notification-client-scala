package com.yetu.notification.client.messages

sealed class Msg

/**
 * Message sent from consumer actor (that talks to MQ) to listener actor
 * @param msg message body
 */
case class MsgDeliver(msg: String) extends Msg


/**
 * Error message sent from consumer actor (that talks to MQ) to listener actor
 * @param error error description
 */
case class MsgError(error: String) extends Msg


/**
 * Acknowledge message when processing is completed (for now not used)
 */
case class MsgProcessed() extends Msg

case class UnknownMsg(m: Any) extends Msg
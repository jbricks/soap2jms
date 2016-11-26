package com.github.soap2jms.common.serialization;

import javax.jms.Message;

import com.github.soap2jms.common.ws.WsJmsMessage;

public interface MessageAndBodyStrategy {
	Message deserializeBody(JMSMessageFactory messageFact, WsJmsMessage wsMessage);
}

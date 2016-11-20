package com.github.soap2jms.model;

import java.util.Map;

import javax.activation.DataHandler;
import javax.jms.BytesMessage;

import com.github.soap2jms.common.ws.WsJmsMessage;

public class S2JBytesMessage extends S2JAbstractDataMessage implements BytesMessage {

	public S2JBytesMessage(String messageId, DataHandler body)  {
		super(messageId, body);
	}

	public S2JBytesMessage(String correlationId, int deliveryMode, long expiration, Map<String, Object> headers,
			String messageId, Integer priority, boolean redelivered, long timestamp, String type, DataHandler body)  {
		super(correlationId, deliveryMode, expiration, headers, messageId, priority, redelivered, timestamp, type, body);
	}

	public S2JBytesMessage(WsJmsMessage message)  {
		super(message);
	}


}

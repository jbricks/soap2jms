package com.github.soap2jms.reader.model;

import java.util.Map;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.StreamMessage;

import com.github.soap2jms.reader.common.ws.WsJmsMessage;

public class S2JStreamMessage extends S2JAbstractDataMessage implements StreamMessage {

	public S2JStreamMessage(String messageId, DataHandler body)  {
		super(messageId, body);
	}

	public S2JStreamMessage(String correlationId, int deliveryMode, long expiration, Map<String, Object> headers,
			String messageId, Integer priority, boolean redelivered, long timestamp, String type, DataHandler body)  {
		super(correlationId, deliveryMode, expiration, headers, messageId, priority, redelivered, timestamp, type,
				body);
	}

	public S2JStreamMessage(WsJmsMessage message)  {
		super(message);
	}

	@Override
	public String readString() throws JMSException {
		return null;
	}

	@Override
	public Object readObject() throws JMSException {
		return null;
	}

	@Override
	public void writeString(String value) throws JMSException {


	}

}

package com.github.soap2jms.model;

import java.util.Map;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.StreamMessage;

public class S2JStreamMessage extends S2JAbstractDataMessage implements StreamMessage {

	public S2JStreamMessage(final String messageId, final DataHandler body) {
		super(messageId, body);
	}

	public S2JStreamMessage(final String correlationId, final int deliveryMode, final long expiration,
			final Map<String, Object> headers, final String clientId, final String messageId, final Integer priority,
			final boolean redelivered, final long timestamp, final String type, final DataHandler body) {
		super(correlationId, deliveryMode, expiration, headers, clientId, messageId, priority, redelivered, timestamp,
				type, body);
	}

	@Override
	public Object readObject() throws JMSException {
		return null;
	}

	@Override
	public String readString() throws JMSException {
		return null;
	}

	@Override
	public void writeString(final String value) throws JMSException {

	}

}

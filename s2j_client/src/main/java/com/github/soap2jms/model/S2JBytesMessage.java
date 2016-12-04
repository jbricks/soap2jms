package com.github.soap2jms.model;

import java.util.Map;

import javax.activation.DataHandler;
import javax.jms.BytesMessage;

public class S2JBytesMessage extends S2JAbstractDataMessage implements BytesMessage {

	public S2JBytesMessage(final String messageId, final DataHandler body) {
		super(messageId, body);
	}

	public S2JBytesMessage(final String correlationId, final int deliveryMode, final long expiration,
			final Map<String, Object> headers, final String messageId, final Integer priority,
			final boolean redelivered, final long timestamp, final String type, final DataHandler body) {
		super(correlationId, deliveryMode, expiration, headers, messageId, priority, redelivered, timestamp, type,
				body);
	}

}

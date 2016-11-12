package com.github.soap2jms.reader.model;

import java.io.IOException;
import java.util.Map;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.io.IOUtils;

import com.github.soap2jms.common.ByteArrayDataSource;
import com.github.soap2jms.reader.common.JMSMessageClassEnum;
import com.github.soap2jms.reader.common.ws.WsJmsMessage;

public class S2JTextMessage extends S2JMessage implements TextMessage {

	
	public S2JTextMessage(final String messageId, final String body) {
		super(JMSMessageClassEnum.TEXT.name(), messageId, new DataHandler(new ByteArrayDataSource(body)));
	}

	public S2JTextMessage(final String correlationId, final int deliveryMode, long expiration,
			final Map<String, Object> headers, final String messageId, final Integer priority,
			final boolean redelivered, final long timestamp, final String type, final String body) {
		super(correlationId, deliveryMode, expiration, headers, messageId, JMSMessageClassEnum.TEXT.name(), priority,
				redelivered, timestamp, type, new DataHandler(new ByteArrayDataSource(body)));
	}

	public S2JTextMessage(final WsJmsMessage message) {
		super(message);
	}

	@Override
	public String getText() throws JMSException {
		String text = null;
		final DataHandler messageBody = this.message.getBody();
		try {
			if (messageBody != null && messageBody.getInputStream() != null) {
				text = IOUtils.toString(messageBody.getInputStream(), "UTF-8");
			}
		} catch (final IOException e) {
			final JMSException e1 = new JMSException("Client error deserializing body", "1");
			e1.initCause(e);
			e1.setLinkedException(e);
			throw e1;
		}
		return text;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean isBodyAssignableTo(final Class c) throws JMSException {
		final DataHandler messageBody = this.message.getBody();
		try {
			if (messageBody == null || messageBody.getInputStream() == null) {
				return true;
			}
			return String.class.equals(c) || Object.class.equals(c);
		} catch (final IOException e) {
			final JMSException e1 = new JMSException("Client error deserializing body", "1");
			e1.initCause(e);
			e1.setLinkedException(e);
			throw e1;
		}
	}

	@Override
	public void setText(final String string) throws JMSException {
		throw new UnsupportedOperationException();
	}

}

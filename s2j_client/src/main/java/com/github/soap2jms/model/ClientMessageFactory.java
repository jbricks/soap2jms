package com.github.soap2jms.model;

import java.io.InputStream;
import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.github.soap2jms.common.serialization.JMSMessageFactory;

public class ClientMessageFactory implements JMSMessageFactory {

	public ClientMessageFactory() {
	}

	@Override
	public BytesMessage createBytesMessage() {
		return null;
	}

	@Override
	public MapMessage createMapMessage() {
		return new S2JMapMessage(null, null);
	}

	@Override
	public ObjectMessage createObjectMessage(final Serializable object) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public StreamMessage createStreamMessage(final InputStream message) {
		// return new S2JStreamMessage(null, null);
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public TextMessage createTextMessage(final String text) {
		return new S2JTextMessage(null, text);
	}

}

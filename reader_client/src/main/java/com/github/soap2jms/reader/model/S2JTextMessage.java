package com.github.soap2jms.reader.model;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.io.IOUtils;

import com.github.soap2jms.reader.common.ws.WsJmsMessage;

public class S2JTextMessage extends S2JMessage implements TextMessage {

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
	public void setText(final String string) throws JMSException {
		// TODO Auto-generated method stub

	}

}

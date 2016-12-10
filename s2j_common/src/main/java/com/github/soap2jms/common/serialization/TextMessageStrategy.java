package com.github.soap2jms.common.serialization;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.jms.Message;

import org.apache.commons.io.IOUtils;

import com.github.soap2jms.common.ws.WsJmsMessage;

public class TextMessageStrategy implements MessageAndBodyStrategy {

	@Override
	public Message deserializeBody(final JMSMessageFactory messageFactory, final WsJmsMessage wsMessage) {
		final DataHandler bodyDH = wsMessage.getBody();
		String bodyText = null;
		try {
			final InputStream inputStream = bodyDH.getInputStream();
			bodyText = IOUtils.toString(inputStream, "UTF-8");
		} catch (final IOException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		return messageFactory.createTextMessage(bodyText);

	}

}

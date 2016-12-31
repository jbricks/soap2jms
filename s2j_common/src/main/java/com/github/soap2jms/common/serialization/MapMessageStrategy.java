package com.github.soap2jms.common.serialization;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.S2JProviderException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.ws.WsJmsMessage;

public class MapMessageStrategy implements MessageAndBodyStrategy {

	@Override
	public Message deserializeBody(final JMSMessageFactory messageFactory, final WsJmsMessage wsMessage)
			throws S2JProviderException,S2JProtocolException {
		final DataHandler dataHandler = wsMessage.getBody();
		final Properties props = new Properties();
		try {
			props.load(dataHandler.getInputStream());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		final MapMessage mapMsg = messageFactory.createMapMessage();
		final Enumeration<?> propertyNames = props.propertyNames();
		while (propertyNames.hasMoreElements()) {
			final String name = (String) propertyNames.nextElement();
			final String value = props.getProperty(name);
			final Object deserializedValue = SoapToJmsSerializer.deserializeValue(value);
			try {
				mapMsg.setObject(name, deserializedValue);
			} catch (final JMSException e) {
				throw new S2JProviderException(StatusCodeEnum.ERR_JMS,
						"Error setting property [" + name + "] to [" + deserializedValue + "]", e.getErrorCode(), e);
			}
		}
		return mapMsg;
	}
}

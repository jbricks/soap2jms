package com.github.soap2jms.pub;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import com.github.soap2jms.common.serialization.JMSImplementation;
import com.github.soap2jms.common.serialization.JmsToSoapSerializer;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.model.S2JMessage;

class ClientSerializationUtils {

	private final JmsToSoapSerializer jms2SoapSerializer;

	public ClientSerializationUtils() {
		this.jms2SoapSerializer = new JmsToSoapSerializer();
	}

	public ClientSerializationUtils(final JmsToSoapSerializer jms2SoapSerializer) {
		this.jms2SoapSerializer = jms2SoapSerializer;
	}

	public WsJmsMessage jmsToSoap(final S2JMessage message) throws JMSException {
		final WsJmsMessage wsMessage = this.jms2SoapSerializer.jmsToSoap(message, JMSImplementation.NONE);
		wsMessage.setClientId(message.getClientId());
		return wsMessage;
	}

	public List<WsJmsMessage> messagesToWs(final S2JMessage[] messages) throws JMSException {
		final List<WsJmsMessage> wsmessages = new ArrayList<>(messages.length);
		for (final S2JMessage message : messages) {
			wsmessages.add(jmsToSoap(message));
		}
		return wsmessages;
	}
}

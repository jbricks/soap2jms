package com.github.soap2jms.common.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.github.soap2jms.common.ByteArrayDataSource;
import com.github.soap2jms.common.JMSMessageClassEnum;
import com.github.soap2jms.common.PropertyTypeEnum;
import com.github.soap2jms.common.ws.StatusCode;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.common.ws.WsJmsMessage.Headers;
import com.github.soap2jms.common.ws.WsJmsMessageAndStatus;

public class JmsToSoapSerializer {

	private static final Map<Class<? extends Message>, JMSMessageClassEnum> ENUM_BY_CLASS = new HashMap<>();
	static {
		ENUM_BY_CLASS.put(BytesMessage.class, JMSMessageClassEnum.BYTE);
		ENUM_BY_CLASS.put(MapMessage.class, JMSMessageClassEnum.MAP);
		ENUM_BY_CLASS.put(ObjectMessage.class, JMSMessageClassEnum.OBJECT);
		ENUM_BY_CLASS.put(StreamMessage.class, JMSMessageClassEnum.STREAM);
		ENUM_BY_CLASS.put(TextMessage.class, JMSMessageClassEnum.TEXT);
	}

	private static DataHandler extractBody(final Message message, final JMSMessageClassEnum messageType)
			throws JMSException {
		byte[] body = null;
		switch (messageType) {
		case TEXT:
			final TextMessage text = (TextMessage) message;
			final String bodyText = text.getText();
			if (bodyText != null) {
				body = bodyText.getBytes();
			}
			break;
		case BYTE:
			body = serializeBytesMessage(message);
			break;
		case OBJECT:
			throw new UnsupportedOperationException("Type " + messageType + "not supported.");
		case STREAM:
			body = serializeStreamMessage(message);
			break;
		case MAP:
			body = serializeMapMessage(message);
			break;
		default:
			throw new UnsupportedOperationException("Type " + messageType + "not supported.");
		}
		final DataSource ds = new ByteArrayDataSource(body, "text/html");
		final DataHandler dh = new DataHandler(ds);
		return dh;
	}

	private static byte[] serializeMapMessage(Message message) throws JMSException {
		Properties props = new Properties();
		MapMessage mm = (MapMessage) message;
		@SuppressWarnings("unchecked")
		final Enumeration<String> messageKeyNames = mm.getMapNames();
		while (messageKeyNames.hasMoreElements()) {
			String name = messageKeyNames.nextElement();
			Object value = mm.getObject(name);
			PropertyTypeEnum type = PropertyTypeEnum.fromObject(value);
			String serializedValue;
			if (type != PropertyTypeEnum.NULL) {
				serializedValue = type.name() + ";" + value.toString();
			} else {
				serializedValue = type.name() + ";";
			}
			props.put(name, serializedValue);
		}
		ByteArrayOutputStream baos;
		try {
			baos = new ByteArrayOutputStream();
			props.store(baos, null);
		} catch (IOException e) {
			throw new RuntimeException("Error writing to ByteArrayOutputStream", e);
		}
		return baos.toByteArray();
	}

	private static byte[] serializeStreamMessage(final Message message) throws JMSException {
		// TODO better stream handling (use a DataHandler to retrieve message at
		// serialization time);
		byte[] body;
		final StreamMessage streamMessage = (StreamMessage) message;
		final ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		final byte[] buffer1 = new byte[4096];
		int i = 0;
		while (-1 != (i = streamMessage.readBytes(buffer1))) {
			baos1.write(buffer1, 0, i);
		}
		body = baos1.toByteArray();
		return body;
	}

	private static byte[] serializeBytesMessage(final Message message) throws JMSException {
		byte[] body;
		final BytesMessage bytesMessage = (BytesMessage) message;
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = bytesMessage.readBytes(buffer))) {
			baos.write(buffer, 0, n);
		}
		body = baos.toByteArray();
		return body;
	}

	public WsJmsMessageAndStatus jmsToSoapMessageAndStatus(final Message message) throws JMSException {
		final WsJmsMessage wsmessage = jmsToSoap(message);

		return new WsJmsMessageAndStatus(wsmessage, new StatusCode("OK", null));

	}

	public WsJmsMessage jmsToSoap(final Message message) throws JMSException {
		JMSMessageClassEnum messageType = JMSMessageClassEnum.UNSUPPORTED;

		for (final Map.Entry<Class<? extends Message>, JMSMessageClassEnum> entry : ENUM_BY_CLASS.entrySet()) {
			if (entry.getKey().isInstance(message)) {
				messageType = entry.getValue();
			}
		}

		List<Headers> headers = convertHeaders(message);

		final DataHandler bodyStream = extractBody(message, messageType);

		final WsJmsMessage wsmessage = new WsJmsMessage(message.getJMSCorrelationID(), message.getJMSDeliveryMode(),
				message.getJMSExpiration(), headers, // headers
				message.getJMSMessageID(), messageType.name(), message.getJMSPriority(), message.getJMSRedelivered(), //
				message.getJMSTimestamp(), message.getJMSType(), // body
				bodyStream);
		return wsmessage;
	}

	private static List<Headers> convertHeaders(final Message message) throws JMSException {
		List<Headers> headers = new ArrayList<>();
		@SuppressWarnings("unchecked")
		final Enumeration<String> propertyNames = message.getPropertyNames();
		while (propertyNames.hasMoreElements()) {
			String name = propertyNames.nextElement();
			Object value = message.getObjectProperty(name);
			PropertyTypeEnum type = PropertyTypeEnum.fromObject(value);
			String serializedValue;
			if (type != PropertyTypeEnum.NULL) {
				serializedValue = type.name() + ";" + value.toString();
			} else {
				serializedValue = type.name() + ";";
			}
			headers.add(new Headers(name, serializedValue));
		}
		return headers;
	}

	public List<WsJmsMessage> messagesToWs(Message[] messages) throws JMSException {
		List<WsJmsMessage> wsmessages = new ArrayList<>(messages.length);
		for(Message message:messages){
			wsmessages.add(jmsToSoap(message));
		}
		return wsmessages;
	}

}

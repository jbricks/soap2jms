package com.github.soap2jms.reader.utils;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
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
import com.github.soap2jms.reader.common.JMSMessageTypeEnum;
import com.github.soap2jms.reader.common.ws.ResponseStatus;
import com.github.soap2jms.reader.common.ws.WsJmsMessage;
import com.github.soap2jms.reader.common.ws.WsJmsMessageAndStatus;

public class ServerSerializationUtils {

	private static final Map<Class<? extends Message>, JMSMessageTypeEnum> ENUM_BY_CLASS = new HashMap<>();
	static {
		ENUM_BY_CLASS.put(BytesMessage.class, JMSMessageTypeEnum.BYTE);
		ENUM_BY_CLASS.put(MapMessage.class, JMSMessageTypeEnum.MAP);
		ENUM_BY_CLASS.put(ObjectMessage.class, JMSMessageTypeEnum.OBJECT);
		ENUM_BY_CLASS.put(StreamMessage.class, JMSMessageTypeEnum.STREAM);
		ENUM_BY_CLASS.put(TextMessage.class, JMSMessageTypeEnum.TEXT);
	}

	private static DataHandler extractBody(final Message message, final JMSMessageTypeEnum messageType)
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
			final BytesMessage bytesMessage = (BytesMessage) message;
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = bytesMessage.readBytes(buffer))) {
				baos.write(buffer, 0, n);
			}
			body = baos.toByteArray();
		case OBJECT:
			throw new UnsupportedOperationException("Type " + messageType + "not supported.");
		case STREAM:
			throw new UnsupportedOperationException("Type " + messageType + "not supported.");
		case MAP:
			new Properties();
			throw new UnsupportedOperationException("Type " + messageType + "not supported.");
		default:
			throw new UnsupportedOperationException("Type " + messageType + "not supported.");
		}
		final DataSource ds = new ByteArrayDataSource(body, "text/html");
		final DataHandler dh = new DataHandler(ds);
		return dh;
	}

	public static WsJmsMessageAndStatus jms2soap(final Message message) throws JMSException {
		JMSMessageTypeEnum messageType = JMSMessageTypeEnum.UNSUPPORTED;

		for (final Map.Entry<Class<? extends Message>, JMSMessageTypeEnum> entry : ENUM_BY_CLASS.entrySet()) {
			if (entry.getKey().isInstance(message)) {
				messageType = entry.getValue();
			}
		}

		final DataHandler bodyStream = extractBody(message, messageType);

		final WsJmsMessage wsmessage = new WsJmsMessage(message.getJMSCorrelationID(), message.getJMSDeliveryMode(),
				null, // headers
				message.getJMSMessageID(), messageType.name(), message.getJMSPriority(), message.getJMSRedelivered(), //
				message.getJMSTimestamp(), message.getJMSType(), // body
				bodyStream);

		return new WsJmsMessageAndStatus(wsmessage, new ResponseStatus("OK", null));

	}
}

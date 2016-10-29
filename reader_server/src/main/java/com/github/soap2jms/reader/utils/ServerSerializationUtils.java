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

import com.github.soap2jms.reader.common.JMSMessageTypeEnum;
import com.github.soap2jms.reader.common.ws.JmsMessage;
import com.github.soap2jms.reader.common.ws.ResponseStatus;
import com.github.soap2jms.reader.common.ws.S2JMessage;
import com.github.soap2jms.reader.common.ws.S2JMessageAndStatus;

public class ServerSerializationUtils {

	private static final Map<Class<? extends Message>, JMSMessageTypeEnum> ENUM_BY_CLASS = new HashMap<>();
	static {
		ENUM_BY_CLASS.put(BytesMessage.class, JMSMessageTypeEnum.BYTE);
		ENUM_BY_CLASS.put(MapMessage.class, JMSMessageTypeEnum.MAP);
		ENUM_BY_CLASS.put(ObjectMessage.class, JMSMessageTypeEnum.OBJECT);
		ENUM_BY_CLASS.put(StreamMessage.class, JMSMessageTypeEnum.STREAM);
		ENUM_BY_CLASS.put(TextMessage.class, JMSMessageTypeEnum.TEXT);
	}

	public static S2JMessageAndStatus jms2soap(Message message) throws JMSException {
		JMSMessageTypeEnum messageType = JMSMessageTypeEnum.UNSUPPORTED;
		
		for (Map.Entry<Class<? extends Message>, JMSMessageTypeEnum> entry : ENUM_BY_CLASS.entrySet()) {
			if (entry.getKey().isInstance(message)) {
				messageType = entry.getValue();
			}
		}

		DataHandler bodyStream = extractBody(message, messageType);
		
		S2JMessage wsmessage = new S2JMessage(message.getJMSMessageID(), message.getJMSTimestamp(), message.getJMSType(),
				message.getJMSCorrelationID(), messageType.name(), null, bodyStream);
		
		return new S2JMessageAndStatus(wsmessage, new ResponseStatus("OK",null));

	}

	private static DataHandler extractBody(Message message, JMSMessageTypeEnum messageType) throws JMSException {
		byte[] body = null;
		switch (messageType) {
		case TEXT:
			TextMessage text = (TextMessage) message;
			String bodyText = text.getText();
			if (bodyText != null) {
				body = bodyText.getBytes();
			}
			break;
		case BYTE:
			BytesMessage bytesMessage = (BytesMessage) message;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        byte[] buffer = new byte[4096];
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
			Properties props=new Properties();
			MapMessage mapMessage = (MapMessage) message;
			
			throw new UnsupportedOperationException("Type " + messageType + "not supported.");
		default:
			throw new UnsupportedOperationException("Type " + messageType + "not supported.");
		}
		DataSource ds = new ByteArrayDataSource(body,"text/html");
		DataHandler dh = new DataHandler(ds);
		return dh;
	}
}

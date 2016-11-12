package com.github.soap2jms.reader.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;

import com.github.soap2jms.reader.common.ErrorType;
import com.github.soap2jms.reader.common.JMSMessageClassEnum;
import com.github.soap2jms.reader.common.PropertyTypeEnum;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.common.ws.WsJmsMessage;
import com.github.soap2jms.reader.common.ws.WsJmsMessage.Headers;
import com.github.soap2jms.reader.common.ws.WsJmsMessageAndStatus;

public class ClientSerializationUtils {
	public static Map<String, Object> convertHeaders(final List<Headers> headers) {
		Map<String, Object> result = new HashMap<>();
		if (headers != null) {
			Map<String, String> parsedProps = new HashMap<>();
			for (final Headers header : headers) {
				final String key = header.getKey();
				final String valueToBeParsed = header.getValue();
				parsedProps.put(key, valueToBeParsed);
			}
			result = deserializePorperties( parsedProps);
		}
		return result;

	}

	private static Map<String, Object>  deserializePorperties( Map<String, String> parsedProps) {
		Map<String, Object> result = new HashMap<>();
		for (Map.Entry<String, String> entry : parsedProps.entrySet()) {
			String valueToBeParsed = entry.getValue();
			final int separatorIndex = valueToBeParsed.indexOf(";");
			if (separatorIndex == -1) {
				throw new S2JProtocolException(ErrorType.INCOMPATIBLE_SERVER_PROTOCOL,
						"Deserializing header values: " + valueToBeParsed);
			}
			final String type = valueToBeParsed.substring(0, separatorIndex);
			final String value = valueToBeParsed.substring(separatorIndex + 1);
			Object parsedValue;
			final PropertyTypeEnum propType = PropertyTypeEnum.valueOf(type);
			switch (propType) {
			case STRING:
				parsedValue = value;
				break;
			case BYTE_ARRAY:
				throw new UnsupportedOperationException();
				// break;
			case INT:
				parsedValue = new Integer(value);
				break;
			case LONG:
				parsedValue = new Long(value);
				break;
			case CHAR:
				parsedValue = new Character(value.charAt(0));
				break;
			case NULL:
				parsedValue = null;
				break;
			default:
				throw new UnsupportedOperationException("Type " + propType + " not yet supported.");
			}
			result.put(entry.getKey(), parsedValue);
		}
		return result;
	}

	public static S2JMessage[] convertMessages(final RetrieveMessageResponseType wsResponse)
			throws S2JProtocolException {
		final S2JMessage[] messages = new S2JMessage[wsResponse.getS2JMessageAndStatus().size()];

		for (int i = 0; i < wsResponse.getS2JMessageAndStatus().size(); i++) {
			// FIXME status
			final WsJmsMessageAndStatus messageAndStatus = wsResponse.getS2JMessageAndStatus().get(i);
			final WsJmsMessage message = messageAndStatus.getWsJmsMessage();
			final String messageType = message.getMessageClass();
			final JMSMessageClassEnum messageTypeEnum = JMSMessageClassEnum.valueOf(messageType);
			switch (messageTypeEnum) {
			case TEXT:
				messages[i] = new S2JTextMessage(message);
				break;
			case BYTE:
				messages[i] = new S2JBytesMessage(message);
				break;
			case STREAM:
				messages[i] = new S2JStreamMessage(message);
				break;
			case MAP:
				messages[i] = deserializeMapMessage(message);
				break;
			default:
				throw new S2JProtocolException(ErrorType.INCOMPATIBLE_SERVER_PROTOCOL, "");
			}
		}

		return messages;
	}

	private static S2JMapMessage deserializeMapMessage(final WsJmsMessage message) {
		DataHandler dataHandler = message.getBody();
		Properties props = new Properties();
		try {
			props.load(dataHandler.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, String> wsBody = (Map) props;
		return new S2JMapMessage(message, deserializePorperties(wsBody));
	}

}

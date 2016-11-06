package com.github.soap2jms.reader.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			for (final Headers header : headers) {
				String key = header.getKey();
				String valueToBeParsed = header.getValue();
				final int separatorIndex = valueToBeParsed.indexOf(";");
				if (separatorIndex == -1) {
					throw new S2JProtocolException(ErrorType.INCOMPATIBLE_SERVER_PROTOCOL,
							"Deserializing header values: " + valueToBeParsed);
				}
				String type = valueToBeParsed.substring(0, separatorIndex);
				String value = valueToBeParsed.substring(separatorIndex + 1);
				Object parsedValue;
				PropertyTypeEnum propType = PropertyTypeEnum.valueOf(type);
				switch (propType) {
				case STRING:
					parsedValue = value;
					break;
				case INT:
					parsedValue = new Integer(value);
					break;
				case LONG:
					parsedValue = new Long(value);
					break;
				case CHAR:
					parsedValue = new Character(value.charAt(0));
					break;
				default:
					throw new UnsupportedOperationException("Type " + propType + " not yet supported.");
				}
				result.put(key, parsedValue);
			}
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
			default:
				throw new S2JProtocolException(ErrorType.INCOMPATIBLE_SERVER_PROTOCOL, "");
			}
		}

		return messages;
	}

}

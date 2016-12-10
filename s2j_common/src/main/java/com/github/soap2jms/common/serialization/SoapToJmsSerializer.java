package com.github.soap2jms.common.serialization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import com.github.soap2jms.common.JMSMessageClassEnum;
import com.github.soap2jms.common.PropertyTypeEnum;
import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.common.ws.WsJmsMessageAndStatus;

public class SoapToJmsSerializer {
	public static Object deserializeValue(final String valueToBeParsed) {
		final int separatorIndex = valueToBeParsed.indexOf(";");
		if (separatorIndex == -1) {
			throw new S2JProtocolException(StatusCodeEnum.ERR_INCOMPATIBLE_PROTOCOL,
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
		return parsedValue;
	}

	private final Map<JMSMessageClassEnum, MessageAndBodyStrategy> messageCreationByMessageType = new HashMap<>();

	/*
	 * private static Map<String, Object> convertHeaders(final List<Headers>
	 * headers) { Map<String, Object> result = new HashMap<>(); if (headers !=
	 * null) { Map<String, String> parsedProps = new HashMap<>(); for (final
	 * Headers header : headers) { final String key = header.getKey(); final
	 * String valueToBeParsed = header.getValue(); parsedProps.put(key,
	 * valueToBeParsed); } result = deserializePorperties(parsedProps); } return
	 * result;
	 * 
	 * }
	 * 
	 * private static Map<String, Object> deserializePorperties(Map<String,
	 * String> parsedProps) { Map<String, Object> result = new HashMap<>(); for
	 * (Map.Entry<String, String> entry : parsedProps.entrySet()) { String
	 * valueToBeParsed = entry.getValue(); Object parsedValue =
	 * deserializeValue(valueToBeParsed); result.put(entry.getKey(),
	 * parsedValue); } return result; }
	 */

	public SoapToJmsSerializer() {
		this.messageCreationByMessageType.put(JMSMessageClassEnum.TEXT, new TextMessageStrategy());
		this.messageCreationByMessageType.put(JMSMessageClassEnum.MAP, new MapMessageStrategy());
	}

	public Message convertMessage(final JMSMessageFactory jmsMessageFactory, final WsJmsMessage wsMessage) {
		return convertSingleMessage(jmsMessageFactory, wsMessage);
	}

	public Message[] convertMessages(final JMSMessageFactory messageFactory,
			final List<WsJmsMessageAndStatus> wsResponse) throws S2JProtocolException {
		final Message[] messages = new Message[wsResponse.size()];

		for (int i = 0; i < wsResponse.size(); i++) {
			// FIXME status
			final WsJmsMessageAndStatus messageAndStatus = wsResponse.get(i);
			final WsJmsMessage wsMessage = messageAndStatus.getWsJmsMessage();
			final Message message = convertSingleMessage(messageFactory, wsMessage);
			messages[i] = message;
		}

		return messages;
	}

	public Message convertSingleMessage(final JMSMessageFactory messageFactory, final WsJmsMessage wsMessage) {
		final String messageType = wsMessage.getMessageClass();
		final JMSMessageClassEnum messageTypeEnum = JMSMessageClassEnum.valueOf(messageType);
		final MessageAndBodyStrategy creationStrategy = this.messageCreationByMessageType.get(messageTypeEnum);
		if (creationStrategy == null) {
			throw new S2JProtocolException(StatusCodeEnum.ERR_INCOMPATIBLE_PROTOCOL,
					"Message " + messageTypeEnum + " not supported.");
		}
		final Message message = creationStrategy.deserializeBody(messageFactory, wsMessage);
		fillHeaders(message, wsMessage.getHeaders());
		try {
			if (wsMessage.getCorrelationId() != null) {
				message.setJMSCorrelationID(wsMessage.getCorrelationId());
			}
			if (wsMessage.getMessageId() != null) {
				message.setJMSMessageID(wsMessage.getMessageId());
			}
			message.setJMSTimestamp(wsMessage.getTimestamp());
			message.setJMSExpiration(wsMessage.getExpiration());
			message.setJMSPriority(wsMessage.getPriority());
			message.setJMSDeliveryMode(wsMessage.getDeliveryMode());
			message.setJMSRedelivered(wsMessage.isRedelivered());
			message.setJMSType(wsMessage.getType());
		} catch (final JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	private void fillHeaders(final Message message, final List<WsJmsMessage.Headers> wsMessage) {
		for (final WsJmsMessage.Headers entry : wsMessage) {
			final String valueToBeParsed = entry.getValue();
			final Object parsedValue = deserializeValue(valueToBeParsed);
			try {
				message.setObjectProperty(entry.getKey(), parsedValue);
			} catch (final JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

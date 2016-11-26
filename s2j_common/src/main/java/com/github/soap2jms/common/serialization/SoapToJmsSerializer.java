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
import com.github.soap2jms.common.ws.WsJmsMessage.Headers;
import com.github.soap2jms.common.ws.WsJmsMessageAndStatus;

public class SoapToJmsSerializer {
	private final Map<JMSMessageClassEnum, MessageAndBodyStrategy> messageCreationByMessageType = new HashMap<>();
	
	public SoapToJmsSerializer() {
		messageCreationByMessageType.put(JMSMessageClassEnum.TEXT, new TextMessageStrategy());
		messageCreationByMessageType.put(JMSMessageClassEnum.MAP, new MapMessageStrategy());
	}

	private static Map<String, Object> convertHeaders(final List<Headers> headers) {
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
			Object parsedValue = deserializeValue(valueToBeParsed);
			result.put(entry.getKey(), parsedValue);
		}
		return result;
	}

	public static Object deserializeValue(String valueToBeParsed) {
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

	public Message[] convertMessages(JMSMessageFactory  messageFactory,final List<WsJmsMessageAndStatus> wsResponse)
			throws S2JProtocolException {
		final Message[] messages = new Message[wsResponse.size()];

		for (int i = 0; i < wsResponse.size(); i++) {
			// FIXME status
			final WsJmsMessageAndStatus messageAndStatus = wsResponse.get(i);
			final WsJmsMessage wsMessage = messageAndStatus.getWsJmsMessage();
			final String messageType = wsMessage.getMessageClass();
			final JMSMessageClassEnum messageTypeEnum = JMSMessageClassEnum.valueOf(messageType);
			MessageAndBodyStrategy creationStrategy = messageCreationByMessageType.get(messageTypeEnum);
			if(creationStrategy == null){
				throw new S2JProtocolException(StatusCodeEnum.ERR_INCOMPATIBLE_PROTOCOL, "Message " + messageTypeEnum + " not supported.");
			}
			messages[i] = creationStrategy.deserializeBody(messageFactory, wsMessage);
			fillHeaders(messages[i],wsMessage.getHeaders());
		}

		return messages;
	}

	private void fillHeaders(Message message, List<WsJmsMessage.Headers> wsMessage) {
		for (WsJmsMessage.Headers entry : wsMessage) {
			String valueToBeParsed = entry.getValue();
			Object parsedValue = deserializeValue(valueToBeParsed);
			try {
				message.setObjectProperty(entry.getKey(), parsedValue);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}



}

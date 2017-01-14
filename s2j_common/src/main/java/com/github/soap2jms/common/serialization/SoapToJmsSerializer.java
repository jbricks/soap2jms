package com.github.soap2jms.common.serialization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.lang3.StringUtils;

import com.github.soap2jms.common.JMSMessageClassEnum;
import com.github.soap2jms.common.PropertyTypeEnum;
import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.S2JProviderException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.ws.WsJmsMessage;

public class SoapToJmsSerializer {
	public static final String ACTIVEMQ_DUPLICATE_ID = "_AMQ_DUPL_ID";

	public static Object deserializeValue(final String valueToBeParsed) throws S2JProtocolException {
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

	public SoapToJmsSerializer() {
		this.messageCreationByMessageType.put(JMSMessageClassEnum.TEXT, new TextMessageStrategy());
		this.messageCreationByMessageType.put(JMSMessageClassEnum.MAP, new MapMessageStrategy());
	}

	public Message convertMessage(final JMSMessageFactory messageFactory, final WsJmsMessage wsMessage,
			final JMSImplementation implementation) throws S2JProviderException, S2JProtocolException {
		final String messageType = wsMessage.getMessageClass();
		final JMSMessageClassEnum messageTypeEnum = JMSMessageClassEnum.valueOf(messageType);
		final MessageAndBodyStrategy creationStrategy = this.messageCreationByMessageType.get(messageTypeEnum);
		if (creationStrategy == null) {
			throw new S2JProtocolException(StatusCodeEnum.ERR_INCOMPATIBLE_PROTOCOL,
					"Message " + messageTypeEnum + " not supported.");
		}
		final Message message = creationStrategy.deserializeBody(messageFactory, wsMessage);
		fillHeaders(message, wsMessage.getHeaders());
		final String clientId = wsMessage.getClientId();
		try {
			if (wsMessage.getJmsCorrelationId() != null) {
				message.setJMSCorrelationID(wsMessage.getJmsCorrelationId());
			}
			if (wsMessage.getJmsMessageId() != null) {
				message.setJMSMessageID(wsMessage.getJmsMessageId());
			}
			message.setJMSDeliveryTime(wsMessage.getJmsDeliveryTime());
			message.setJMSExpiration(wsMessage.getJmsExpiration());
			message.setJMSPriority(wsMessage.getJmsPriority());
			message.setJMSRedelivered(wsMessage.isJmsRedelivered());
			message.setJMSTimestamp(wsMessage.getJmsTimestamp());
			message.setJMSType(wsMessage.getJmsType());
			if (JMSImplementation.ARTEMIS_ACTIVE_MQ.equals(implementation) && StringUtils.isNotEmpty(clientId)) {
				message.setStringProperty(ACTIVEMQ_DUPLICATE_ID, clientId);
			} else if (JMSImplementation.NONE.equals(implementation)) {
				message.setJMSDeliveryMode(wsMessage.getJmsDeliveryMode());
			}
		} catch (final JMSException e) {
			throw new S2JProviderException(StatusCodeEnum.ERR_JMS,
					"Error setting jms properties clientId[" + clientId + "]", e.getErrorCode(), e);
		}

		return message;
	}

	private void fillHeaders(final Message message, final List<WsJmsMessage.Headers> wsMessage)
			throws S2JProviderException, S2JProtocolException {
		for (final WsJmsMessage.Headers entry : wsMessage) {
			final String valueToBeParsed = entry.getValue();
			final Object parsedValue = deserializeValue(valueToBeParsed);
			try {
				message.setObjectProperty(entry.getKey(), parsedValue);
			} catch (final JMSException e) {
				throw new S2JProviderException(StatusCodeEnum.ERR_JMS,
						"Error setting jms propertiy [" + entry.getKey() + "] to value [" + parsedValue + "]",
						e.getErrorCode(), e);
			}
		}
	}

}

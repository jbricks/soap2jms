package com.github.soap2jms.reader.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.soap2jms.reader.common.ErrorType;
import com.github.soap2jms.reader.common.JMSMessageTypeEnum;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.common.ws.WsJmsMessage;
import com.github.soap2jms.reader.common.ws.WsJmsMessage.Headers;
import com.github.soap2jms.reader.common.ws.WsJmsMessageAndStatus;

public class ClientSerializationUtils {
	public static Map<String, Object> convertHeaders(final List<Headers> headers) {
		new HashMap<>();
		for (final Headers header : headers) {
			header.getKey();
			header.getValue();

		}
		return null;
	}

	public static S2JMessage[] convertMessages(final RetrieveMessageResponseType wsdlResponse)
			throws S2JProtocolException {
		final S2JMessage[] messages = new S2JMessage[wsdlResponse.getS2JMessageAndStatus().size()];

		for (int i = 0; i < wsdlResponse.getS2JMessageAndStatus().size(); i++) {
			// FIXME status
			final WsJmsMessageAndStatus messageAndStatus = wsdlResponse.getS2JMessageAndStatus().get(i);
			final WsJmsMessage message = messageAndStatus.getWsJmsMessage();
			final String messageType = message.getMessageClass();
			final JMSMessageTypeEnum messageTypeEnum = JMSMessageTypeEnum.valueOf(messageType);

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

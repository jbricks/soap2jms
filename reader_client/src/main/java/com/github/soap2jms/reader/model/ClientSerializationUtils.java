package com.github.soap2jms.reader.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.soap2jms.reader.common.ErrorType;
import com.github.soap2jms.reader.common.JMSMessageTypeEnum;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.common.ws.S2JMessage.Headers;
import com.github.soap2jms.reader.common.ws.S2JMessageAndStatus;

public class ClientSerializationUtils {
	public static Map<String, Object> convertHeaders(List<Headers> headers) {
		Map<String, Object> headerMap = new HashMap<>();
		for (Headers header : headers) {
			String key = header.getKey();
			String value = header.getValue();

		}
		return null;
	}

	public static S2JMessage[] convertMessages(RetrieveMessageResponseType wsdlResponse) throws S2JProtocolException {
		S2JMessage[] messages = new S2JMessage[wsdlResponse.getS2JMessageAndStatus().size()];

		for (int i = 0; i < wsdlResponse.getS2JMessageAndStatus().size(); i++) {
			// FIXME status
			com.github.soap2jms.reader.common.ws.S2JMessageAndStatus messageAndStatus = wsdlResponse
					.getS2JMessageAndStatus().get(i);
			com.github.soap2jms.reader.common.ws.S2JMessage message = messageAndStatus.getS2JMessage();
			String messageType = message.getMessageType();
			JMSMessageTypeEnum messageTypeEnum = JMSMessageTypeEnum.valueOf(messageType);

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

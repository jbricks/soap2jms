package com.github.soap2jms.reader.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;

import org.junit.Test;

import com.github.soap2jms.common.ByteArrayDataSource;
import com.github.soap2jms.reader.common.JMSMessageTypeEnum;
import com.github.soap2jms.reader.common.ws.ResponseStatus;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.common.ws.WsJmsMessage;
import com.github.soap2jms.reader.common.ws.WsJmsMessageAndStatus;

public class ClientSerializationUtilsTest {

	@Test
	public void testClientSerializationUtils() throws Exception {
		List<WsJmsMessageAndStatus> messagesIn = new ArrayList<WsJmsMessageAndStatus>();
		DataHandler body = new DataHandler(new ByteArrayDataSource("test".getBytes(),"text/html"));
		WsJmsMessage s2jMessage = new WsJmsMessage("correlationId", 1, null, // headers
				"messageId", JMSMessageTypeEnum.TEXT.name(), 1, // priority
				true, 1000L, // timestamp
				"type", body);
		ResponseStatus messageStatus = new ResponseStatus("OK", null);
		messagesIn.add(new WsJmsMessageAndStatus(s2jMessage, messageStatus));
		RetrieveMessageResponseType wsdlResponse = new RetrieveMessageResponseType(messagesIn, false);
		S2JMessage[] messages = ClientSerializationUtils.convertMessages(wsdlResponse);
		assertNotNull("Result is not null", messages);
		assertEquals("Messages deserialized", 1, messages.length);
		S2JMessage message = messages[0];
		assertEquals("Message class", S2JTextMessage.class, message.getClass());
		assertEquals("Message body", "test", ((S2JTextMessage) message).getText());

	}

}

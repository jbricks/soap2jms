package com.github.soap2jms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.jms.Message;

import org.junit.Test;

import com.github.soap2jms.common.ByteArrayDataSource;
import com.github.soap2jms.common.JMSMessageClassEnum;
import com.github.soap2jms.common.serialization.JMSMessageFactory;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.StatusCode;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.common.ws.WsJmsMessageAndStatus;

public class ClientSerializationUtilsTest {

	@Test
	public void testClientSerializationUtils() throws Exception {
		List<WsJmsMessageAndStatus> messagesIn = new ArrayList<WsJmsMessageAndStatus>();
		DataHandler body = new DataHandler(new ByteArrayDataSource("test".getBytes(),"text/html"));
		WsJmsMessage s2jMessage = new WsJmsMessage("correlationId", 1, 0, null, // headers
				"messageId", JMSMessageClassEnum.TEXT.name(), 1, // priority
				true, 1000L, // timestamp
				"type", body);
		StatusCode messageStatus = new StatusCode("OK", null);
		messagesIn.add(new WsJmsMessageAndStatus(s2jMessage, messageStatus));
		RetrieveMessageResponseType wsdlResponse = new RetrieveMessageResponseType(messagesIn, false);
		JMSMessageFactory messageFactory=new ClientMessageFactory();
		Message[] messages = new SoapToJmsSerializer().convertMessages(messageFactory, wsdlResponse.getS2JMessageAndStatus());
		assertNotNull("Result is not null", messages);
		assertEquals("Messages deserialized", 1, messages.length);
		S2JMessage message = (S2JMessage) messages[0];
		assertEquals("Message class", S2JTextMessage.class, message.getClass());
		assertEquals("Message body", "test", ((S2JTextMessage) message).getText());
		assertEquals("MessageId ", "messageId",message.getJMSMessageID());
	}

}

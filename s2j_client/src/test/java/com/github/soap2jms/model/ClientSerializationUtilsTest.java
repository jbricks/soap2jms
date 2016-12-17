package com.github.soap2jms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.jms.Message;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.github.soap2jms.common.ByteArrayDataSource;
import com.github.soap2jms.common.JMSMessageClassEnum;
import com.github.soap2jms.common.serialization.JMSImplementation;
import com.github.soap2jms.common.serialization.JMSMessageFactory;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.StatusCode;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.common.ws.WsJmsMessageAndStatus;
import com.github.soap2jms.pub.ClientSerializationUtils;

public class ClientSerializationUtilsTest {

	/**
	 * test soap -> jms(S2J implementation) used when read messages
	 * 
	 * @throws Exception
	 */
	@Test
	public void testClientSoapToJmsS2J() throws Exception {
		final List<WsJmsMessageAndStatus> messagesIn = new ArrayList<>();
		final DataHandler body = new DataHandler(new ByteArrayDataSource("test".getBytes(), "text/html"));
		final WsJmsMessage s2jMessage = new WsJmsMessage(null, // headers
				"correlationId", 1, 0L, 0L, // expiration
				"messageId", 1, // priority
				true, 1000L, // timestamp
				"type", "clientId", JMSMessageClassEnum.TEXT.name(), body);
		final StatusCode messageStatus = new StatusCode("OK", null);
		messagesIn.add(new WsJmsMessageAndStatus(s2jMessage, messageStatus));
		final RetrieveMessageResponseType wsdlResponse = new RetrieveMessageResponseType(messagesIn, false);
		final JMSMessageFactory messageFactory = new ClientMessageFactory();
		final Message[] messages = new SoapToJmsSerializer().convertMessages(messageFactory,
				wsdlResponse.getS2JMessageAndStatus(), JMSImplementation.NONE);
		assertNotNull("Result is not null", messages);
		assertEquals("Messages deserialized", 1, messages.length);
		final S2JMessage message = (S2JMessage) messages[0];
		assertEquals("Message class", S2JTextMessage.class, message.getClass());
		assertEquals("Message body", "test", ((S2JTextMessage) message).getText());
		assertEquals("MessageId ", "messageId", message.getJMSMessageID());
		//assertEquals("ClientId ", "clientId", message.getClientId());
	}

	/**
	 * test jms -> soap used when send messages
	 * 
	 * @throws Exception
	 */
	@Test
	public void testClientSoapToJms() throws Exception {

		S2JTextMessage s2jt = new S2JTextMessage("clientId", "body");
		s2jt.setJMSMessageID("messageId");
		s2jt.setJMSType("type");
		ClientSerializationUtils cls = new ClientSerializationUtils();
		WsJmsMessage message = cls.jmsToSoap(s2jt);

		assertEquals("MessageId ", "messageId", message.getJmsMessageId());
		assertEquals("ClientId ", "clientId", message.getClientId());
		assertEquals("MessageType ", "type", message.getJmsType());
		assertEquals("Body", "body", IOUtils.toString(message.getBody().getInputStream(), "UTF-8"));
	}
}

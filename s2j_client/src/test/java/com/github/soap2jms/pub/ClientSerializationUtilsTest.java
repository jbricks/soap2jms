package com.github.soap2jms.pub;

import static org.junit.Assert.assertEquals;

import javax.activation.DataHandler;
import javax.jms.Message;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.github.soap2jms.common.ByteArrayDataSource;
import com.github.soap2jms.common.JMSMessageClassEnum;
import com.github.soap2jms.common.serialization.JMSImplementation;
import com.github.soap2jms.common.serialization.JMSMessageFactory;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.model.ClientMessageFactory;
import com.github.soap2jms.model.S2JMessage;
import com.github.soap2jms.model.S2JTextMessage;

public class ClientSerializationUtilsTest {

	/**
	 * test jms -> soap used when send messages
	 *
	 * @throws Exception
	 */
	@Test
	public void testClientSoapToJms() throws Exception {

		final S2JTextMessage s2jt = new S2JTextMessage("clientId", "body");
		s2jt.setJMSMessageID("messageId");
		s2jt.setJMSType("type");
		final ClientSerializationUtils cls = new ClientSerializationUtils();
		final WsJmsMessage message = cls.jmsToSoap(s2jt);

		assertEquals("MessageId ", "messageId", message.getJmsMessageId());
		assertEquals("ClientId ", "clientId", message.getClientId());
		assertEquals("MessageType ", "type", message.getJmsType());
		assertEquals("Body", "body", IOUtils.toString(message.getBody().getInputStream(), "UTF-8"));
	}

	/**
	 * test soap -> jms(S2J implementation) used when read messages
	 *
	 * @throws Exception
	 */
	@Test
	public void testClientSoapToJmsS2J() throws Exception {
		final DataHandler body = new DataHandler(new ByteArrayDataSource("test".getBytes(), "text/html"));
		final WsJmsMessage s2jMessage = new WsJmsMessage(null, // headers
				"correlationId", 1, 0L, 0L, // expiration
				"messageId", 1, // priority
				true, 1000L, // timestamp
				"type", "clientId", JMSMessageClassEnum.TEXT.name(), body);

		final JMSMessageFactory messageFactory = new ClientMessageFactory();
		final Message message1 = new SoapToJmsSerializer().convertMessage(messageFactory, s2jMessage,
				JMSImplementation.NONE);
		final S2JMessage message = (S2JMessage) message1;
		assertEquals("Message class", S2JTextMessage.class, message.getClass());
		assertEquals("Message body", "test", ((S2JTextMessage) message).getText());
		assertEquals("MessageId ", "messageId", message.getJMSMessageID());
		// assertEquals("ClientId ", "clientId", message.getClientId());
	}
}

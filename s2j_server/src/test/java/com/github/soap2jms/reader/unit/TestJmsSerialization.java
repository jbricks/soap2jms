package com.github.soap2jms.reader.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.junit.Test;

import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.serialization.JmsToSoapSerializer;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.WsJmsMessageAndStatus;
import com.github.soap2jms.model.ClientMessageFactory;
import com.github.soap2jms.model.S2JMapMessage;
import com.github.soap2jms.model.S2JMessage;
import com.github.soap2jms.model.S2JTextMessage;

public class TestJmsSerialization {

	private S2JMessage serializeAndDeserializeMessage(final Message serverMessage)
			throws JMSException, S2JProtocolException {
		final WsJmsMessageAndStatus jms2soap = new JmsToSoapSerializer().jmsToSoapMessageAndStatus(serverMessage);
		final RetrieveMessageResponseType wsdlResponse = new RetrieveMessageResponseType();
		wsdlResponse.getS2JMessageAndStatus().add(jms2soap);
		final Message[] convertMessages = new SoapToJmsSerializer().convertMessages(new ClientMessageFactory(),
				wsdlResponse.getS2JMessageAndStatus());
		assertEquals("Deserialized message num", 1, convertMessages.length);
		final S2JMessage message = (S2JMessage) convertMessages[0];
		return message;
	}

	@Test
	public void testIntegerHeaders() throws Exception {
		final Message serverMessage = new S2JTextMessage("1", "test");
		serverMessage.setIntProperty("test1", 1);
		serverMessage.setObjectProperty("test2", 2);

		final S2JMessage message = serializeAndDeserializeMessage(serverMessage);
		assertTrue("There are headers", message.getPropertyNames().hasMoreElements());
		assertTrue("Header test1 is present", message.propertyExists("test1"));
		assertTrue("Header test2 is present", message.propertyExists("test2"));
		assertEquals("Header test1 has the right value", 1, message.getIntProperty("test1"));
		assertEquals("Header test2", 2, message.getIntProperty("test2"));
		assertEquals("Header test2", Integer.class, message.getObjectProperty("test2").getClass());
		assertEquals("Header test2 can be converted to string", "2", message.getStringProperty("test2"));
	}

	@Test
	public void testMapMessage() throws Exception {
		final Map<String, Object> map = new HashMap<>();
		map.put("string", "string");
		map.put("int", 1);
		final Message serverMessage = new S2JMapMessage("1", map);
		final Message message = serializeAndDeserializeMessage(serverMessage);
		assertTrue("instance of ", message instanceof MapMessage);
		final MapMessage mapMessage = (MapMessage) message;
		assertTrue("body string is present", mapMessage.itemExists("string"));
		assertEquals("Body int ", 1, mapMessage.getInt("int"));
		assertEquals("body string ", "string", mapMessage.getString("string"));
		assertEquals("body int class in getObject", Integer.class, mapMessage.getObject("int").getClass());
		assertEquals("body returns null if a key doesn't exist", null, mapMessage.getObject("not exist"));
	}

	@Test
	public void testNullHeaders() throws Exception {
		final Message serverMessage = new S2JTextMessage("1", "test");
		final S2JMessage message = serializeAndDeserializeMessage(serverMessage);
		assertFalse("headers size", message.getPropertyNames().hasMoreElements());
	}

	@Test
	public void testStringHeaders() throws Exception {
		final Message serverMessage = new S2JTextMessage("1", "test");
		serverMessage.setStringProperty("testName", "testValue");
		final S2JMessage message = serializeAndDeserializeMessage(serverMessage);
		assertTrue("There are headers", message.getPropertyNames().hasMoreElements());
		assertTrue("Header testName is present", message.propertyExists("testName"));
		assertEquals("Header testName has the right value", "testValue", message.getStringProperty("testName"));
	}

}

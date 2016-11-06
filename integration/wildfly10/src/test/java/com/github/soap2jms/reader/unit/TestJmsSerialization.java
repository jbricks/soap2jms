package com.github.soap2jms.reader.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.Message;

import org.junit.Test;

import com.github.soap2jms.common.ByteArrayDataSource;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.common.ws.WsJmsMessageAndStatus;
import com.github.soap2jms.reader.model.ClientSerializationUtils;
import com.github.soap2jms.reader.model.S2JMessage;
import com.github.soap2jms.reader.model.S2JProtocolException;
import com.github.soap2jms.reader.model.S2JTextMessage;
import com.github.soap2jms.reader.utils.ServerSerializationUtils;

public class TestJmsSerialization {
	
	@Test
	public void testNullHeaders() throws Exception{
		Message serverMessage=new S2JTextMessage("1",new DataHandler(new ByteArrayDataSource("test")));
		S2JMessage message = serializeAndDeserializeMessage(serverMessage);	
		assertFalse("headers size",message.getPropertyNames().hasMoreElements());
	}

	@Test
	public void testStringHeaders() throws Exception{
		Message serverMessage=new S2JTextMessage("1",new DataHandler(new ByteArrayDataSource("test")));
		serverMessage.setStringProperty("testName", "testValue");
		S2JMessage message = serializeAndDeserializeMessage(serverMessage);	
		assertTrue("There are headers",message.getPropertyNames().hasMoreElements());
		assertEquals("Header testName is present","testValue", message.getStringProperty("testName"));
	}
	
	private S2JMessage serializeAndDeserializeMessage(Message serverMessage) throws JMSException, S2JProtocolException {
		WsJmsMessageAndStatus jms2soap = ServerSerializationUtils.jms2soap(serverMessage);
		RetrieveMessageResponseType wsdlResponse=new RetrieveMessageResponseType();
		wsdlResponse.getS2JMessageAndStatus().add(jms2soap);
		S2JMessage[] convertMessages = ClientSerializationUtils.convertMessages(wsdlResponse);
		assertEquals("Deserialized message num",1,convertMessages.length);
		S2JMessage message = convertMessages[0];
		return message;
	}
	
}

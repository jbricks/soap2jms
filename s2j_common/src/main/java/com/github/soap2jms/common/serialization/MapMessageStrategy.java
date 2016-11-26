package com.github.soap2jms.common.serialization;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import com.github.soap2jms.common.ws.WsJmsMessage;

public class MapMessageStrategy implements MessageAndBodyStrategy {


	@Override
	public Message deserializeBody(JMSMessageFactory messageFactory, WsJmsMessage wsMessage) {
		DataHandler dataHandler = wsMessage.getBody();
		Properties props = new Properties();
		try {
			props.load(dataHandler.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	
		MapMessage mapMsg = messageFactory.createMapMessage();
		final Enumeration<?> propertyNames = props.propertyNames();
		while (propertyNames.hasMoreElements()){
			String name = (String) propertyNames.nextElement();
			String value = props.getProperty(name);
			final Object deserializedValue = SoapToJmsSerializer.deserializeValue(value);
			try {
				mapMsg.setObject(name, deserializedValue);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mapMsg;
	}
}

package com.github.soap2jms.model;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.github.soap2jms.common.serialization.JMSMessageFactory;

public class ClientMessageFactory implements JMSMessageFactory {

	public ClientMessageFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ObjectMessage createObjectMessage(Serializable object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapMessage createMapMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BytesMessage createBytesMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextMessage createTextMessage(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StreamMessage createStreamMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}

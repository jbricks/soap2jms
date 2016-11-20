package com.github.soap2jms.reader.utils;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

public interface JMSMessageFactory {
	ObjectMessage createObjectMessage(Serializable object);
	ObjectMessage createObjectMessage();
	Message createMessage();
	MapMessage createMapMessage();
	BytesMessage createBytesMessage();
	TextMessage createTextMessage(String text);
	StreamMessage createStreamMessage();
}

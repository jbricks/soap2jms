package com.github.soap2jms.common.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

public interface JMSMessageFactory {
	ObjectMessage createObjectMessage(Serializable object);
	MapMessage createMapMessage();
	BytesMessage createBytesMessage();
	TextMessage createTextMessage(String text);
	StreamMessage createStreamMessage(InputStream stream) throws IOException, JMSException;
}

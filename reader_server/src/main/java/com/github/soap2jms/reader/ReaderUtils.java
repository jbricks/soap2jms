package com.github.soap2jms.reader;

import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.github.soap2jms.reader_common.JmsMessage;

final class ReaderUtils {
	
	private static final Map<Class<? extends Message>,JMSMessageTypeEnum> ENUM_BY_CLASS=new HashMap<>();
	static{
		ENUM_BY_CLASS.put(	BytesMessage.class,JMSMessageTypeEnum.BYTE);
		ENUM_BY_CLASS.put(	MapMessage.class,JMSMessageTypeEnum.MAP);
		ENUM_BY_CLASS.put(	ObjectMessage.class,JMSMessageTypeEnum.OBJECT);
		ENUM_BY_CLASS.put(	StreamMessage.class,JMSMessageTypeEnum.STREAM);
		ENUM_BY_CLASS.put(	TextMessage.class,JMSMessageTypeEnum.TEXT);
	}
	JmsMessage jms2soap(Message message) throws JMSException {
		byte[] body=null;
		JMSMessageTypeEnum messageType=JMSMessageTypeEnum.UNSUPPORTED;
		for(Map.Entry<Class<? extends Message>,JMSMessageTypeEnum> entry:ENUM_BY_CLASS.entrySet()){
			if(message.getClass().isAssignableFrom(entry.getKey())){
				messageType = entry.getValue();
			}
		}
		JmsMessage result = new JmsMessage(message.getJMSMessageID(), message.getJMSTimestamp(), message.getJMSType(), 
				message.getJMSCorrelationID(), messageType.name(), body);
		return result;
				
	}
}

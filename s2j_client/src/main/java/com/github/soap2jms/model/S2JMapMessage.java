package com.github.soap2jms.model;

import java.util.Enumeration;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageFormatException;

import org.apache.commons.collections4.iterators.IteratorEnumeration;

import com.github.soap2jms.common.JMSMessageClassEnum;
import com.github.soap2jms.common.ws.WsJmsMessage;

public class S2JMapMessage extends S2JMessage implements MapMessage {

	private final Map<String, Object> body;

	public S2JMapMessage(String correlationId, int deliveryMode, long expiration, Map<String, Object> headers,
			String messageId, Integer priority, boolean redelivered, long timestamp, String type,
			Map<String, Object> body) {
		super(correlationId, deliveryMode, expiration, headers, messageId, JMSMessageClassEnum.MAP.name(), priority,
				redelivered, timestamp, type, null);
		this.body = body;
	}

	public S2JMapMessage(String messageId, Map<String, Object> body) {
		super(JMSMessageClassEnum.MAP.name(), messageId, null);
		this.body = body;
	}

	public S2JMapMessage(WsJmsMessage message, Map<String, Object> body) {
		super(message);
		this.body = body;
		super.message.setBody(null);
	}

	protected <T> T getNotNullBodyPart(String name, Class<T> expectedClass) throws MessageFormatException {
		T result = getBodyPart(name,expectedClass);

		if (result == null) {
			throw new MessageFormatException("property [" + name + "] type " + expectedClass + " null");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getBodyPart(String name, Class<T> expectedClass) throws MessageFormatException {
		if(!body.containsKey(name)){
			throw new MessageFormatException("property [" + name + "] type " + expectedClass + " not found");
		}
		
		Object propValue = body.get(name);

		if (propValue == null) {
			return null;
		}
		T result;
		if (expectedClass.isInstance(propValue)) {
			result = (T) propValue;
		} else if (String.class.equals(expectedClass)) {
			result = (T) propValue.toString();
		} else {
			throw new MessageFormatException("property [" + name + "] of type " + expectedClass.getName()
					+ " can't be assigned to " + expectedClass);
		}

		return result;
	}
	
	@Override
	public boolean isBodyAssignableTo(Class c) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBody(final Class<T> c) throws JMSException {
		if (body == null) {
			return null;
		}
		if (!c.isInstance(body)) {
			throw new MessageFormatException("Map message can't be assigned to class " + c);
		}
		return (T) body;
	}

	@Override
	public boolean getBoolean(String name) throws JMSException {
		return getNotNullBodyPart(name, Boolean.class);
	}

	@Override
	public byte getByte(String name) throws JMSException {
		return getNotNullBodyPart(name, Byte.class);
	}

	@Override
	public short getShort(String name) throws JMSException {
		return getNotNullBodyPart(name, Short.class);
	}

	@Override
	public char getChar(String name) throws JMSException {
		return getNotNullBodyPart(name, Character.class);
	}

	@Override
	public int getInt(String name) throws JMSException {
		return getNotNullBodyPart(name, Integer.class);
	}

	@Override
	public long getLong(String name) throws JMSException {
		return getNotNullBodyPart(name, Long.class);
	}

	@Override
	public float getFloat(String name) throws JMSException {
		return getNotNullBodyPart(name, Float.class);
	}

	@Override
	public double getDouble(String name) throws JMSException {
		return getNotNullBodyPart(name, Double.class);
	}

	@Override
	public String getString(String name) throws JMSException {
		return getNotNullBodyPart(name, String.class);
	}

	@Override
	public byte[] getBytes(String name) throws JMSException {
		return getNotNullBodyPart(name, byte[].class);
	}

	@Override
	public Object getObject(String name) throws JMSException {
		return body.get(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getMapNames() throws JMSException {
		return new IteratorEnumeration<String>(body.keySet().iterator());
	}

	@Override
	public void setBoolean(String name, boolean value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setByte(String name, byte value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setShort(String name, short value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setChar(String name, char value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setInt(String name, int value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setLong(String name, long value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setFloat(String name, float value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setDouble(String name, double value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setString(String name, String value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setBytes(String name, byte[] value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public void setBytes(String name, byte[] value, int offset, int length) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setObject(String name, Object value) throws JMSException {
		body.put(name, value);

	}

	@Override
	public boolean itemExists(String name) throws JMSException {
		return body.containsKey(name);
	}

}

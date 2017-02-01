package com.github.soap2jms.model;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageFormatException;

import org.apache.commons.collections4.iterators.IteratorEnumeration;

public class S2JMapMessage extends S2JMessage implements MapMessage {

	private final Map<String, Object> body;

	public S2JMapMessage(final String correlationId, final int deliveryMode, final long expiration,
			final Map<String, Object> headers, final String clientId, final String messageId, final Integer priority,
			final boolean redelivered, final long timestamp, final String type, final Map<String, Object> body) {
		super(correlationId, deliveryMode, expiration, headers, clientId, messageId, priority, redelivered, timestamp,
				type, null);
		this.body = body;
	}

	public S2JMapMessage(final String clientId, final Map<String, Object> body) {
		super(clientId, null);
		this.body = (body == null ? new HashMap<>() : body);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBody(final Class<T> c) throws JMSException {
		if (this.body == null) {
			return null;
		}
		if (!c.isInstance(this.body)) {
			throw new MessageFormatException("Map message can't be assigned to class " + c);
		}
		return (T) this.body;
	}

	@SuppressWarnings("unchecked")
	protected <T> T getBodyPart(final String name, final Class<T> expectedClass) throws MessageFormatException {
		if (!this.body.containsKey(name)) {
			throw new MessageFormatException("property [" + name + "] type " + expectedClass + " not found");
		}

		final Object propValue = this.body.get(name);

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
	public boolean getBoolean(final String name) throws JMSException {
		return getNotNullBodyPart(name, Boolean.class);
	}

	@Override
	public byte getByte(final String name) throws JMSException {
		return getNotNullBodyPart(name, Byte.class);
	}

	@Override
	public byte[] getBytes(final String name) throws JMSException {
		return getNotNullBodyPart(name, byte[].class);
	}

	@Override
	public char getChar(final String name) throws JMSException {
		return getNotNullBodyPart(name, Character.class);
	}

	@Override
	public double getDouble(final String name) throws JMSException {
		return getNotNullBodyPart(name, Double.class);
	}

	@Override
	public float getFloat(final String name) throws JMSException {
		return getNotNullBodyPart(name, Float.class);
	}

	@Override
	public int getInt(final String name) throws JMSException {
		return getNotNullBodyPart(name, Integer.class);
	}

	@Override
	public long getLong(final String name) throws JMSException {
		return getNotNullBodyPart(name, Long.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getMapNames() throws JMSException {
		return new IteratorEnumeration<>(this.body.keySet().iterator());
	}

	protected <T> T getNotNullBodyPart(final String name, final Class<T> expectedClass) throws MessageFormatException {
		final T result = getBodyPart(name, expectedClass);

		if (result == null) {
			throw new MessageFormatException("property [" + name + "] type " + expectedClass + " null");
		}
		return result;
	}

	@Override
	public Object getObject(final String name) throws JMSException {
		return this.body.get(name);
	}

	@Override
	public short getShort(final String name) throws JMSException {
		return getNotNullBodyPart(name, Short.class);
	}

	@Override
	public String getString(final String name) throws JMSException {
		return getNotNullBodyPart(name, String.class);
	}

	@Override
	public boolean isBodyAssignableTo(final Class c) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean itemExists(final String name) throws JMSException {
		return this.body.containsKey(name);
	}

	@Override
	public void setBoolean(final String name, final boolean value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setByte(final String name, final byte value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setBytes(final String name, final byte[] value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setBytes(final String name, final byte[] value, final int offset, final int length)
			throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setChar(final String name, final char value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setDouble(final String name, final double value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setFloat(final String name, final float value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setInt(final String name, final int value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setLong(final String name, final long value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setObject(final String name, final Object value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setShort(final String name, final short value) throws JMSException {
		this.body.put(name, value);

	}

	@Override
	public void setString(final String name, final String value) throws JMSException {
		this.body.put(name, value);

	}

}

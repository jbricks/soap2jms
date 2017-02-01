package com.github.soap2jms.model;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.activation.DataHandler;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;

import org.apache.commons.collections4.iterators.IteratorEnumeration;

public abstract class S2JMessage implements Message {

	protected DataHandler body;
	private final String clientId;
	private final Map<String, Object> headers;
	private String jmsCorrelationId;
	private Integer jmsDeliveryMode;
	private long jmsDeliveryTime;
	private long jmsExpiration;
	private String jmsMessageId;
	private Integer jmsPriority;
	boolean jmsRedelivered;
	private long jmsTimestamp;
	private String jmsType;

	protected S2JMessage(final String clientId, final DataHandler body) {
		this(null, 0, 0, new HashMap<String, Object>(), clientId, null, 0, false, System.currentTimeMillis(), null,
				body);
	}

	public S2JMessage(final String jmsCorrelationId, final int jmsDeliveryMode, final long jmsExpiration,
			final Map<String, Object> headers, final String clientId, final String jmsMessageId,
			final Integer jmsPriority, final boolean jmsRedelivered, final long jmsTimestamp, final String type,
			final DataHandler body) {
		this.jmsCorrelationId = jmsCorrelationId;
		this.jmsDeliveryMode = jmsDeliveryMode;
		this.jmsExpiration = jmsExpiration;
		this.headers = headers;
		this.jmsMessageId = jmsMessageId;
		this.clientId = clientId;
		this.jmsPriority = jmsPriority;
		this.jmsRedelivered = jmsRedelivered;
		this.jmsTimestamp = jmsTimestamp;
		this.jmsType = type;
		this.body = body;
	}

	@Override
	public void acknowledge() throws JMSException {
		throw new UnsupportedOperationException("Acknowledge not supported at the moment");

	}

	@Override
	public void clearBody() throws JMSException {
		this.body = null;
	}

	@Override
	public void clearProperties() throws JMSException {
		this.headers.clear();
	}

	@Override
	public <T> T getBody(final Class<T> c) throws JMSException {
		return null;
	}

	@Override
	public boolean getBooleanProperty(final String name) throws JMSException {
		return getProperty(name, Boolean.class);
	}

	@Override
	public byte getByteProperty(final String name) throws JMSException {
		return getProperty(name, Byte.class);
	}

	public String getClientId() {
		return this.clientId;
	}

	@Override
	public double getDoubleProperty(final String name) throws JMSException {
		return getProperty(name, Double.class);
	}

	@Override
	public float getFloatProperty(final String name) throws JMSException {
		return getProperty(name, Float.class);
	}

	@Override
	public int getIntProperty(final String name) throws JMSException {
		return getProperty(name, Integer.class);
	}

	@Override
	public String getJMSCorrelationID() throws JMSException {
		return this.jmsCorrelationId;
	}

	@Override
	public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		return this.jmsCorrelationId == null ? null : this.jmsCorrelationId.getBytes();
	}

	@Override
	public int getJMSDeliveryMode() throws JMSException {
		return this.jmsDeliveryMode;
	}

	@Override
	public long getJMSDeliveryTime() throws JMSException {
		return this.jmsDeliveryTime;
	}

	@Override
	public Destination getJMSDestination() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getJMSExpiration() throws JMSException {
		return this.jmsExpiration;
	}

	@Override
	public String getJMSMessageID() throws JMSException {
		return this.jmsMessageId;
	}

	@Override
	public int getJMSPriority() throws JMSException {
		return this.jmsPriority;
	}

	@Override
	public boolean getJMSRedelivered() throws JMSException {
		return this.jmsRedelivered;
	}

	@Override
	public javax.jms.Destination getJMSReplyTo() throws JMSException {
		return null;
	}

	@Override
	public long getJMSTimestamp() throws JMSException {
		return this.jmsTimestamp;
	}

	@Override
	public String getJMSType() throws JMSException {
		return this.jmsType;
	}

	@Override
	public long getLongProperty(final String name) throws JMSException {
		return getProperty(name, Long.class);
	}

	protected <T> T getNotNullProperty(final String name, final Class<T> expectedClass) throws MessageFormatException {
		final T result = getProperty(name, expectedClass);

		if (result == null) {
			throw new MessageFormatException("property [" + name + "] type " + expectedClass + " null");
		}
		return result;
	}

	@Override
	public Object getObjectProperty(final String name) throws JMSException {
		return this.headers.get(name);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getProperty(final String name, final Class<T> expectedClass) throws MessageFormatException {
		if (!this.headers.containsKey(name)) {
			throw new MessageFormatException("property [" + name + "] type " + expectedClass + " not found");
		}

		final Object propValue = this.headers.get(name);

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
	public Enumeration<String> getPropertyNames() throws JMSException {
		final Iterator<String> iterator = this.headers.keySet().iterator();
		return new IteratorEnumeration<>(iterator);
	}

	@Override
	public short getShortProperty(final String name) throws JMSException {
		return getProperty(name, Short.class);
	}

	@Override
	public String getStringProperty(final String name) throws JMSException {
		return getProperty(name, String.class);
	}

	@Override
	public boolean propertyExists(final String name) throws JMSException {
		return this.headers.containsKey(name);
	}

	@Override
	public void setBooleanProperty(final String name, final boolean value) throws JMSException {
		this.headers.put(name, value);

	}

	@Override
	public void setByteProperty(final String name, final byte value) throws JMSException {
		this.headers.put(name, value);

	}

	@Override
	public void setDoubleProperty(final String name, final double value) throws JMSException {
		this.headers.put(name, value);

	}

	@Override
	public void setFloatProperty(final String name, final float value) throws JMSException {
		this.headers.put(name, value);

	}

	@Override
	public void setIntProperty(final String name, final int value) throws JMSException {
		this.headers.put(name, value);

	}

	@Override
	public void setJMSCorrelationID(final String correlationID) throws JMSException {
		this.jmsCorrelationId = correlationID;
	}

	@Override
	public void setJMSCorrelationIDAsBytes(final byte[] correlationID) throws JMSException {
		this.jmsCorrelationId = new String(correlationID);
	}

	@Override
	public void setJMSDeliveryMode(final int deliveryMode) throws JMSException {
		this.jmsDeliveryMode = deliveryMode;
	}

	@Override
	public void setJMSDeliveryTime(final long deliveryTime) throws JMSException {
		this.jmsDeliveryTime = deliveryTime;
	}

	@Override
	public void setJMSDestination(final Destination destination) throws JMSException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setJMSExpiration(final long expiration) throws JMSException {
		this.jmsExpiration = expiration;

	}

	@Override
	public void setJMSMessageID(final String id) throws JMSException {
		this.jmsMessageId = id;
	}

	@Override
	public void setJMSPriority(final int priority) throws JMSException {
		this.jmsPriority = priority;
	}

	@Override
	public void setJMSRedelivered(final boolean redelivered) throws JMSException {
		this.jmsRedelivered = redelivered;

	}

	@Override
	public void setJMSReplyTo(final Destination replyTo) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSTimestamp(final long timestamp) throws JMSException {
		this.jmsTimestamp = timestamp;

	}

	@Override
	public void setJMSType(final String type) throws JMSException {
		this.jmsType = type;

	}

	@Override
	public void setLongProperty(final String name, final long value) throws JMSException {
		this.headers.put(name, value);
	}

	@Override
	public void setObjectProperty(final String name, final Object value) throws JMSException {
		this.headers.put(name, value);
	}

	@Override
	public void setShortProperty(final String name, final short value) throws JMSException {
		this.headers.put(name, value);
	}

	@Override
	public void setStringProperty(final String name, final String value) throws JMSException {
		this.headers.put(name, value);
	}

}

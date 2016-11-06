package com.github.soap2jms.reader.model;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import com.github.soap2jms.reader.common.ws.WsJmsMessage;
import com.github.soap2jms.reader.common.ws.WsJmsMessage.Headers;

public abstract class S2JMessage implements Message {

	private final Map<String, Object> headers;
	protected final WsJmsMessage message;

	protected S2JMessage(String messageClass, final String messageId,
			final DataHandler body) {
		this(new WsJmsMessage(null, 0, null, messageId, messageClass, 0, false, System.currentTimeMillis(), null,
				body));
	}

	protected S2JMessage(final String correlationId, final int deliveryMode, final Map<String, Object> headers,
			final String messageId, final String messageClass, final Integer priority, final boolean redelivered,
			final long timestamp, final String type, final DataHandler body) {
		message = new WsJmsMessage(correlationId, deliveryMode, null, messageId, messageClass, priority, redelivered,
				timestamp, type, body);
		this.headers = headers;
	}

	public S2JMessage(final WsJmsMessage message) {
		this.message = message;
		this.headers = ClientSerializationUtils.convertHeaders(message.getHeaders());
	}

	@Override
	public void acknowledge() throws JMSException {
		throw new UnsupportedOperationException("Acknowledge not supported at the moment");

	}

	@Override
	public void clearBody() throws JMSException {
		this.message.setBody(null);
	}

	@Override
	public void clearProperties() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getBody(final Class<T> c) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBooleanProperty(final String name) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByteProperty(final String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDoubleProperty(final String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloatProperty(final String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIntProperty(final String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getJMSCorrelationID() throws JMSException {
		return this.message.getCorrelationId();
	}

	@Override
	public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		return null;
	}

	@Override
	public int getJMSDeliveryMode() throws JMSException {
		return this.message.getDeliveryMode();
	}

	@Override
	public long getJMSDeliveryTime() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Destination getJMSDestination() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getJMSExpiration() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getJMSMessageID() throws JMSException {
		return this.message.getMessageId();
	}

	@Override
	public int getJMSPriority() throws JMSException {
		return this.message.getPriority() == null ? 0 : this.message.getPriority();
	}

	@Override
	public boolean getJMSRedelivered() throws JMSException {
		return this.message.isRedelivered();
	}

	@Override
	public Destination getJMSReplyTo() throws JMSException {
		return null;
	}

	@Override
	public long getJMSTimestamp() throws JMSException {
		return this.message.getTimestamp();
	}

	@Override
	public String getJMSType() throws JMSException {
		return this.message.getType();
	}

	@Override
	public long getLongProperty(final String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getObjectProperty(final String name) throws JMSException {
		return this.headers.get(name);
	}

	@Override
	public Enumeration<String> getPropertyNames() throws JMSException {
		return null;
	}

	@Override
	public short getShortProperty(final String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStringProperty(final String name) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBodyAssignableTo(final Class c) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean propertyExists(final String name) throws JMSException {
		return this.headers.containsKey(name);
	}

	@Override
	public void setBooleanProperty(final String name, final boolean value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setByteProperty(final String name, final byte value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDoubleProperty(final String name, final double value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFloatProperty(final String name, final float value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIntProperty(final String name, final int value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSCorrelationID(final String correlationID) throws JMSException {
		this.message.setCorrelationId(correlationID);
	}

	@Override
	public void setJMSCorrelationIDAsBytes(final byte[] correlationID) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSDeliveryMode(final int deliveryMode) throws JMSException {
		this.message.setDeliveryMode(deliveryMode);
	}

	@Override
	public void setJMSDeliveryTime(final long deliveryTime) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSDestination(final Destination destination) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSExpiration(final long expiration) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSMessageID(final String id) throws JMSException {
		this.message.setMessageId(id);
	}

	@Override
	public void setJMSPriority(final int priority) throws JMSException {
		this.message.setPriority(priority);
	}

	@Override
	public void setJMSRedelivered(final boolean redelivered) throws JMSException {
		this.message.setRedelivered(redelivered);

	}

	@Override
	public void setJMSReplyTo(final Destination replyTo) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSTimestamp(final long timestamp) throws JMSException {
		this.message.setTimestamp(timestamp);

	}

	@Override
	public void setJMSType(final String type) throws JMSException {
		this.message.setType(type);

	}

	@Override
	public void setLongProperty(final String name, final long value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setObjectProperty(final String name, final Object value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShortProperty(final String name, final short value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStringProperty(final String name, final String value) throws JMSException {
		// TODO Auto-generated method stub

	}

}

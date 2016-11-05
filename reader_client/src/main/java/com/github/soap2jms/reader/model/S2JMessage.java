package com.github.soap2jms.reader.model;

import java.util.Enumeration;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public abstract class S2JMessage implements Message {

	protected final com.github.soap2jms.reader.common.ws.S2JMessage message;
	private final Map<String, Object> headers;
	
	public S2JMessage(com.github.soap2jms.reader.common.ws.S2JMessage message) {
		this.message = message;
		headers = ClientSerializationUtils.convertHeaders(message.getHeaders());
	}

	@Override
	public String getJMSMessageID() throws JMSException {
		return message.getMessageId();
	}

	@Override
	public void setJMSMessageID(String id) throws JMSException {
		 message.setMessageId(id);
	}

	@Override
	public long getJMSTimestamp() throws JMSException {
		return message.getTimestamp();
	}

	@Override
	public void setJMSTimestamp(long timestamp) throws JMSException {
		message.setTimestamp(timestamp);

	}

	@Override
	public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		return null;
	}

	@Override
	public void setJMSCorrelationIDAsBytes(byte[] correlationID) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setJMSCorrelationID(String correlationID) throws JMSException {
		message.setCorrelationId(correlationID);
	}

	@Override
	public String getJMSCorrelationID() throws JMSException {
		return message.getCorrelationId();
	}

	@Override
	public Destination getJMSReplyTo() throws JMSException {
		return null;
	}

	@Override
	public void setJMSReplyTo(Destination replyTo) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public Destination getJMSDestination() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJMSDestination(Destination destination) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getJMSDeliveryMode() throws JMSException {
		return message.getDeliveryMode();
	}

	@Override
	public void setJMSDeliveryMode(int deliveryMode) throws JMSException {
		message.setDeliveryMode(deliveryMode);
	}

	@Override
	public boolean getJMSRedelivered() throws JMSException {
		return message.isRedelivered();
	}

	@Override
	public void setJMSRedelivered(boolean redelivered) throws JMSException {
		message.setRedelivered(redelivered);

	}

	@Override
	public String getJMSType() throws JMSException {
		return message.getType();
	}

	@Override
	public void setJMSType(String type) throws JMSException {
		message.setType(type);

	}

	@Override
	public long getJMSExpiration() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setJMSExpiration(long expiration) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public long getJMSDeliveryTime() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setJMSDeliveryTime(long deliveryTime) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getJMSPriority() throws JMSException {
		return message.getPriority() == null ? 0 : message.getPriority();
	}

	@Override
	public void setJMSPriority(int priority) throws JMSException {
		message.setPriority(priority);
	}

	@Override
	public void clearProperties() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean propertyExists(String name) throws JMSException {
		return headers.containsKey(name);
	}

	@Override
	public boolean getBooleanProperty(String name) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByteProperty(String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShortProperty(String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIntProperty(String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLongProperty(String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloatProperty(String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDoubleProperty(String name) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStringProperty(String name) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObjectProperty(String name) throws JMSException {
		return headers.get(name);
	}

	@Override
	public Enumeration<String> getPropertyNames() throws JMSException {
		return null;
	}

	@Override
	public void setBooleanProperty(String name, boolean value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setByteProperty(String name, byte value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShortProperty(String name, short value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIntProperty(String name, int value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLongProperty(String name, long value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFloatProperty(String name, float value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDoubleProperty(String name, double value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStringProperty(String name, String value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setObjectProperty(String name, Object value) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public void acknowledge() throws JMSException {
		throw new UnsupportedOperationException("Acknowledge not supported at the moment");

	}

	@Override
	public void clearBody() throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getBody(Class<T> c) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBodyAssignableTo(Class c) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

}

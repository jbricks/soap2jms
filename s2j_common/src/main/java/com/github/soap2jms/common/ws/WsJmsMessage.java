
package com.github.soap2jms.common.ws;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for wsJmsMessage complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="wsJmsMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryMode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="expiration" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="headers" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="messageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="messageClass">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="TEXT"/>
 *               &lt;enumeration value="MAP"/>
 *               &lt;enumeration value="BYTES"/>
 *               &lt;enumeration value="STREAM"/>
 *               &lt;enumeration value="OBJECT"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="redelivered" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="body" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsJmsMessage", propOrder = { "correlationId", "deliveryMode", "expiration", "headers", "messageId",
		"messageClass", "priority", "redelivered", "timestamp", "type", "body" })
public class WsJmsMessage {

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "key", "value" })
	public static class Headers {

		@XmlElement(required = true)
		protected String key;
		@XmlElement(required = true)
		protected String value;

		public Headers() {
		}

		public Headers(final String key, final String value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Gets the value of the key property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getKey() {
			return this.key;
		}

		/**
		 * Gets the value of the value property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getValue() {
			return this.value;
		}

		/**
		 * Sets the value of the key property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setKey(final String value) {
			this.key = value;
		}

		/**
		 * Sets the value of the value property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setValue(final String value) {
			this.value = value;
		}

	}

	protected DataHandler body;
	protected String correlationId;
	protected int deliveryMode;
	protected long expiration;
	@XmlElement(required = true)
	protected List<WsJmsMessage.Headers> headers;
	@XmlElement(required = true)
	protected String messageClass;
	@XmlElement(required = true)
	protected String messageId;
	protected Integer priority;
	protected boolean redelivered;
	protected long timestamp;

	@XmlElement(required = true)
	protected String type;

	public WsJmsMessage() {
	}

	public WsJmsMessage(final String correlationId, final int deliveryMode, final long expiration,
			final List<Headers> headers, final String messageId, final String messageClass, final Integer priority,
			final boolean redelivered, final long timestamp, final String type, final DataHandler body) {
		this.correlationId = correlationId;
		this.deliveryMode = deliveryMode;
		this.expiration = expiration;
		this.headers = headers;
		this.messageId = messageId;
		this.messageClass = messageClass;
		this.priority = priority;
		this.redelivered = redelivered;
		this.timestamp = timestamp;
		this.type = type;
		this.body = body;
	}

	/**
	 * Gets the value of the body property.
	 * 
	 * @return possible object is byte[]
	 */
	public DataHandler getBody() {
		return this.body;
	}

	/**
	 * Gets the value of the correlationId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCorrelationId() {
		return this.correlationId;
	}

	/**
	 * Gets the value of the deliveryMode property.
	 * 
	 */
	public int getDeliveryMode() {
		return this.deliveryMode;
	}

	/**
	 * Gets the value of the expiration property.
	 * 
	 */
	public long getExpiration() {
		return this.expiration;
	}

	/**
	 * Gets the value of the headers property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the headers property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getHeaders().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link WsJmsMessage.Headers }
	 * 
	 * 
	 */
	public List<WsJmsMessage.Headers> getHeaders() {
		if (this.headers == null) {
			this.headers = new ArrayList<>();
		}
		return this.headers;
	}

	/**
	 * Gets the value of the messageClass property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMessageClass() {
		return this.messageClass;
	}

	/**
	 * Gets the value of the messageId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMessageId() {
		return this.messageId;
	}

	/**
	 * Gets the value of the priority property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getPriority() {
		return this.priority;
	}

	/**
	 * Gets the value of the timestamp property.
	 * 
	 */
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Gets the value of the redelivered property.
	 * 
	 */
	public boolean isRedelivered() {
		return this.redelivered;
	}

	/**
	 * Sets the value of the body property.
	 * 
	 * @param value
	 *            allowed object is byte[]
	 */
	public void setBody(final DataHandler value) {
		this.body = value;
	}

	/**
	 * Sets the value of the correlationId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCorrelationId(final String value) {
		this.correlationId = value;
	}

	/**
	 * Sets the value of the deliveryMode property.
	 * 
	 */
	public void setDeliveryMode(final int value) {
		this.deliveryMode = value;
	}

	/**
	 * Sets the value of the expiration property.
	 * 
	 */
	public void setExpiration(final long value) {
		this.expiration = value;
	}

	/**
	 * Sets the value of the messageClass property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMessageClass(final String value) {
		this.messageClass = value;
	}

	/**
	 * Sets the value of the messageId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMessageId(final String value) {
		this.messageId = value;
	}

	/**
	 * Sets the value of the priority property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setPriority(final Integer value) {
		this.priority = value;
	}

	/**
	 * Sets the value of the redelivered property.
	 * 
	 */
	public void setRedelivered(final boolean value) {
		this.redelivered = value;
	}

	/**
	 * Sets the value of the timestamp property.
	 * 
	 */
	public void setTimestamp(final long value) {
		this.timestamp = value;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setType(final String value) {
		this.type = value;
	}

}

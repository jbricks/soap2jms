
package com.github.soap2jms.common.ws;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="jmsCorrelationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jmsDeliveryMode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="jmsDeliveryTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="jmsExpiration" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="jmsMessageId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jmsPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="jmsRedelivered" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="jmsTimestamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="jmsType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="clientId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "wsJmsMessage", propOrder = { "headers", "jmsCorrelationId", "jmsDeliveryMode", "jmsDeliveryTime",
		"jmsExpiration", "jmsMessageId", "jmsPriority", "jmsRedelivered", "jmsTimestamp", "jmsType", "clientId",
		"messageClass", "body" })
public class WsJmsMessage {

	@XmlElement(required = true)
	protected List<WsJmsMessage.Headers> headers;
	protected String jmsCorrelationId;
	protected int jmsDeliveryMode;
	protected Long jmsDeliveryTime;
	protected long jmsExpiration;
	protected String jmsMessageId;
	protected Integer jmsPriority;
	protected boolean jmsRedelivered;
	protected long jmsTimestamp;
	@XmlElement(required = true)
	protected String jmsType;
	protected String clientId;
	@XmlElement(required = true)
	protected String messageClass;
	protected DataHandler body;

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
		if (headers == null) {
			headers = new ArrayList<WsJmsMessage.Headers>();
		}
		return this.headers;
	}

	/**
	 * Gets the value of the jmsCorrelationId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getJmsCorrelationId() {
		return jmsCorrelationId;
	}

	/**
	 * Sets the value of the jmsCorrelationId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setJmsCorrelationId(String value) {
		this.jmsCorrelationId = value;
	}

	/**
	 * Gets the value of the jmsDeliveryMode property.
	 * 
	 */
	public int getJmsDeliveryMode() {
		return jmsDeliveryMode;
	}

	/**
	 * Sets the value of the jmsDeliveryMode property.
	 * 
	 */
	public void setJmsDeliveryMode(int value) {
		this.jmsDeliveryMode = value;
	}

	/**
	 * Gets the value of the jmsDeliveryTime property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getJmsDeliveryTime() {
		return jmsDeliveryTime;
	}

	/**
	 * Sets the value of the jmsDeliveryTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setJmsDeliveryTime(Long value) {
		this.jmsDeliveryTime = value;
	}

	/**
	 * Gets the value of the jmsExpiration property.
	 * 
	 */
	public long getJmsExpiration() {
		return jmsExpiration;
	}

	/**
	 * Sets the value of the jmsExpiration property.
	 * 
	 */
	public void setJmsExpiration(long value) {
		this.jmsExpiration = value;
	}

	/**
	 * Gets the value of the jmsMessageId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getJmsMessageId() {
		return jmsMessageId;
	}

	/**
	 * Sets the value of the jmsMessageId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setJmsMessageId(String value) {
		this.jmsMessageId = value;
	}

	/**
	 * Gets the value of the jmsPriority property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getJmsPriority() {
		return jmsPriority;
	}

	/**
	 * Sets the value of the jmsPriority property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setJmsPriority(Integer value) {
		this.jmsPriority = value;
	}

	/**
	 * Gets the value of the jmsRedelivered property.
	 * 
	 */
	public boolean isJmsRedelivered() {
		return jmsRedelivered;
	}

	/**
	 * Sets the value of the jmsRedelivered property.
	 * 
	 */
	public void setJmsRedelivered(boolean value) {
		this.jmsRedelivered = value;
	}

	/**
	 * Gets the value of the jmsTimestamp property.
	 * 
	 */
	public long getJmsTimestamp() {
		return jmsTimestamp;
	}

	/**
	 * Sets the value of the jmsTimestamp property.
	 * 
	 */
	public void setJmsTimestamp(long value) {
		this.jmsTimestamp = value;
	}

	/**
	 * Gets the value of the jmsType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getJmsType() {
		return jmsType;
	}

	/**
	 * Sets the value of the jmsType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setJmsType(String value) {
		this.jmsType = value;
	}

	/**
	 * Gets the value of the clientId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Sets the value of the clientId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setClientId(String value) {
		this.clientId = value;
	}

	/**
	 * Gets the value of the messageClass property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMessageClass() {
		return messageClass;
	}

	/**
	 * Sets the value of the messageClass property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMessageClass(String value) {
		this.messageClass = value;
	}

	/**
	 * Gets the value of the body property.
	 * 
	 * @return possible object is byte[]
	 */
	public byte[] getBody() {
		return body;
	}

	/**
	 * Sets the value of the body property.
	 * 
	 * @param value
	 *            allowed object is byte[]
	 */
	public void setBody(byte[] value) {
		this.body = value;
	}

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

		/**
		 * Gets the value of the key property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getKey() {
			return key;
		}

		/**
		 * Sets the value of the key property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setKey(String value) {
			this.key = value;
		}

		/**
		 * Gets the value of the value property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Sets the value of the value property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setValue(String value) {
			this.value = value;
		}

	}

}

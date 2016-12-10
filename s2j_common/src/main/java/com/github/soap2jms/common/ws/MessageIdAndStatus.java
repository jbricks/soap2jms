
package com.github.soap2jms.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.github.soap2jms.common.StatusCodeEnum;

/**
 * <p>
 * Java class for messageIdAndStatus complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="messageIdAndStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://soap2jms.github.com/common/ws}statusCode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageIdAndStatus", propOrder = { "messageId", "status" })
public class MessageIdAndStatus {

	@XmlElement(required = true)
	protected String messageId;
	@XmlElement(required = true)
	protected StatusCode status;

	public MessageIdAndStatus() {
	}

	public MessageIdAndStatus(final String messageId) {
		this.messageId = messageId;
		this.status = new StatusCode("OK", null);
	}

	public MessageIdAndStatus(final String messageId, final StatusCodeEnum status, final String reason) {
		this.messageId = messageId;
		this.status = new StatusCode(status.name(), reason);
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
	 * Gets the value of the status property.
	 *
	 * @return possible object is {@link StatusCode }
	 *
	 */
	public StatusCode getStatus() {
		return this.status;
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
	 * Sets the value of the status property.
	 *
	 * @param value
	 *            allowed object is {@link StatusCode }
	 *
	 */
	public void setStatus(final StatusCode value) {
		this.status = value;
	}

}

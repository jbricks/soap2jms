
package com.github.soap2jms.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for wsJmsMessageAndStatus complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="wsJmsMessageAndStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wsJmsMessage" type="{http://soap2jms.github.com/common/ws}wsJmsMessage"/>
 *         &lt;element name="messageStatus" type="{http://soap2jms.github.com/common/ws}statusCode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsJmsMessageAndStatus", propOrder = { "wsJmsMessage", "messageStatus" })
public class WsJmsMessageAndStatus {

	@XmlElement(required = true)
	protected StatusCode messageStatus;
	@XmlElement(required = true)
	protected WsJmsMessage wsJmsMessage;

	public WsJmsMessageAndStatus() {

	}

	public WsJmsMessageAndStatus(final WsJmsMessage wsJmsMessage, final StatusCode messageStatus) {
		this.wsJmsMessage = wsJmsMessage;
		this.messageStatus = messageStatus;
	}

	/**
	 * Gets the value of the messageStatus property.
	 *
	 * @return possible object is {@link StatusCode }
	 *
	 */
	public StatusCode getMessageStatus() {
		return this.messageStatus;
	}

	/**
	 * Gets the value of the wsJmsMessage property.
	 *
	 * @return possible object is {@link WsJmsMessage }
	 *
	 */
	public WsJmsMessage getWsJmsMessage() {
		return this.wsJmsMessage;
	}

	/**
	 * Sets the value of the messageStatus property.
	 *
	 * @param value
	 *            allowed object is {@link StatusCode }
	 *
	 */
	public void setMessageStatus(final StatusCode value) {
		this.messageStatus = value;
	}

	/**
	 * Sets the value of the wsJmsMessage property.
	 *
	 * @param value
	 *            allowed object is {@link WsJmsMessage }
	 *
	 */
	public void setWsJmsMessage(final WsJmsMessage value) {
		this.wsJmsMessage = value;
	}

}
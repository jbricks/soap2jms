
package com.github.soap2jms.common.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queueName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="messageSetIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messages" type="{http://soap2jms.github.com/common/ws}wsJmsMessage" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "queueName", "messageSetIdentifier", "messages" })
@XmlRootElement(name = "sendMessages")
public class SendMessages {

	@XmlElement(required = true)
	protected List<WsJmsMessage> messages;
	protected String messageSetIdentifier;
	@XmlElement(required = true)
	protected String queueName;

	/**
	 * Gets the value of the messages property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the messages property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getMessages().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link WsJmsMessage }
	 *
	 *
	 */
	public List<WsJmsMessage> getMessages() {
		if (this.messages == null) {
			this.messages = new ArrayList<>();
		}
		return this.messages;
	}

	/**
	 * Gets the value of the messageSetIdentifier property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getMessageSetIdentifier() {
		return this.messageSetIdentifier;
	}

	/**
	 * Gets the value of the queueName property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getQueueName() {
		return this.queueName;
	}

	/**
	 * Sets the value of the messageSetIdentifier property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setMessageSetIdentifier(final String value) {
		this.messageSetIdentifier = value;
	}

	/**
	 * Sets the value of the queueName property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setQueueName(final String value) {
		this.queueName = value;
	}

}

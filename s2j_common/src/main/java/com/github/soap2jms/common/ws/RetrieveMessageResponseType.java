
package com.github.soap2jms.common.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for retrieveMessageResponseType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="retrieveMessageResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="s2jMessages" type="{http://soap2jms.github.com/common/ws}wsJmsMessage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="complete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retrieveMessageResponseType", propOrder = { "s2JMessages", "complete" })
public class RetrieveMessageResponseType {

	protected boolean complete;
	@XmlElement(name = "s2jMessages")
	protected List<WsJmsMessage> s2JMessages;

	public RetrieveMessageResponseType() {

	}

	public RetrieveMessageResponseType(final List<WsJmsMessage> s2jMessages, final boolean complete) {
		this.s2JMessages = s2jMessages;
		this.complete = complete;
	}

	/**
	 * Gets the value of the s2JMessages property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the s2JMessages property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getS2JMessages().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link WsJmsMessage }
	 * 
	 * 
	 */
	public List<WsJmsMessage> getS2JMessages() {
		if (this.s2JMessages == null) {
			this.s2JMessages = new ArrayList<>();
		}
		return this.s2JMessages;
	}

	/**
	 * Gets the value of the complete property.
	 * 
	 */
	public boolean isComplete() {
		return this.complete;
	}

	/**
	 * Sets the value of the complete property.
	 * 
	 */
	public void setComplete(final boolean value) {
		this.complete = value;
	}

}

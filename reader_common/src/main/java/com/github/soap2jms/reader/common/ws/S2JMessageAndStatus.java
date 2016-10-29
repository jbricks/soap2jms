
package com.github.soap2jms.reader.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for s2jMessageAndStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="s2jMessageAndStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="s2jMessage" type="{http://soap2jms.github.com/reader/common/ws}s2jMessage"/>
 *         &lt;element name="messageStatus" type="{http://soap2jms.github.com/reader/common/ws}responseStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "s2jMessageAndStatus", propOrder = {
    "s2JMessage",
    "messageStatus"
})
public class S2JMessageAndStatus {

    @XmlElement(name = "s2jMessage", required = true)
    protected S2JMessage s2JMessage;
    @XmlElement(required = true)
    protected ResponseStatus messageStatus;

    public S2JMessageAndStatus() {
		// TODO Auto-generated constructor stub
	}

	public S2JMessageAndStatus(S2JMessage s2jMessage, ResponseStatus messageStatus) {
		s2JMessage = s2jMessage;
		this.messageStatus = messageStatus;
	}

	/**
     * Gets the value of the s2JMessage property.
     * 
     * @return
     *     possible object is
     *     {@link S2JMessage }
     *     
     */
    public S2JMessage getS2JMessage() {
        return s2JMessage;
    }

    /**
     * Sets the value of the s2JMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link S2JMessage }
     *     
     */
    public void setS2JMessage(S2JMessage value) {
        this.s2JMessage = value;
    }

    /**
     * Gets the value of the messageStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseStatus }
     *     
     */
    public ResponseStatus getMessageStatus() {
        return messageStatus;
    }

    /**
     * Sets the value of the messageStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseStatus }
     *     
     */
    public void setMessageStatus(ResponseStatus value) {
        this.messageStatus = value;
    }

}

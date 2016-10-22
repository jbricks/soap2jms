
package com.github.soap2jms.reader_common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for jmsMessageAndStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="jmsMessageAndStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jmsMessage" type="{http://soap2jms.github.com/reader_common}jmsMessage"/>
 *         &lt;element name="messageStatus" type="{http://soap2jms.github.com/reader_common}responseStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jmsMessageAndStatus", propOrder = {
    "jmsMessage",
    "messageStatus"
})
public class JmsMessageAndStatus {

    @XmlElement(required = true)
    protected JmsMessage jmsMessage;
    @XmlElement(required = true)
    protected ResponseStatus messageStatus;

    /**
     * Gets the value of the jmsMessage property.
     * 
     * @return
     *     possible object is
     *     {@link JmsMessage }
     *     
     */
    public JmsMessage getJmsMessage() {
        return jmsMessage;
    }

    /**
     * Sets the value of the jmsMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JmsMessage }
     *     
     */
    public void setJmsMessage(JmsMessage value) {
        this.jmsMessage = value;
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

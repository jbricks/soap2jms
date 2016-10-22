
package com.github.soap2jms.reader_common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for retrieveMessageResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="retrieveMessageResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responseStatus" type="{http://soap2jms.github.com/reader_common}responseStatus"/>
 *         &lt;element name="jmsMessageAndStatus" type="{http://soap2jms.github.com/reader_common}jmsMessageAndStatus" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "retrieveMessageResponseType", propOrder = {
    "responseStatus",
    "jmsMessageAndStatus",
    "complete"
})
public class RetrieveMessageResponseType {

    @XmlElement(required = true)
    protected ResponseStatus responseStatus;
    protected List<JmsMessageAndStatus> jmsMessageAndStatus;
    protected boolean complete;

    /**
     * Gets the value of the responseStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseStatus }
     *     
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Sets the value of the responseStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseStatus }
     *     
     */
    public void setResponseStatus(ResponseStatus value) {
        this.responseStatus = value;
    }

    /**
     * Gets the value of the jmsMessageAndStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jmsMessageAndStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJmsMessageAndStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JmsMessageAndStatus }
     * 
     * 
     */
    public List<JmsMessageAndStatus> getJmsMessageAndStatus() {
        if (jmsMessageAndStatus == null) {
            jmsMessageAndStatus = new ArrayList<JmsMessageAndStatus>();
        }
        return this.jmsMessageAndStatus;
    }

    /**
     * Gets the value of the complete property.
     * 
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Sets the value of the complete property.
     * 
     */
    public void setComplete(boolean value) {
        this.complete = value;
    }

}


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
 * &lt;complexType name="retrieveMessageResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="jmsMessages" type="{http://soap2jms.github.com/reader_common}jmsMessage" maxOccurs="unbounded"/&gt;
 *         &lt;element name="complete" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retrieveMessageResponseType", propOrder = {
    "jmsMessages",
    "complete"
})
public class RetrieveMessageResponseType {

    @XmlElement(required = true)
    protected List<JmsMessage> jmsMessages;
    protected boolean complete;

    /**
     * Gets the value of the jmsMessages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jmsMessages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJmsMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JmsMessage }
     * 
     * 
     */
    public List<JmsMessage> getJmsMessages() {
        if (jmsMessages == null) {
            jmsMessages = new ArrayList<JmsMessage>();
        }
        return this.jmsMessages;
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

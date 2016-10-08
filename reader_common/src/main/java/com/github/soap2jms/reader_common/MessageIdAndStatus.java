
package com.github.soap2jms.reader_common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageIdAndStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messageIdAndStatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence maxOccurs="unbounded"&gt;
 *         &lt;element name="messageId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="status" type="{http://soap2jms.github.com/reader_common}acknowledgeStatus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageIdAndStatus", propOrder = {
    "messageIdAndStatus"
})
public class MessageIdAndStatus {

    @XmlElements({
        @XmlElement(name = "messageId", required = true, type = String.class),
        @XmlElement(name = "status", required = true, type = AcknowledgeStatus.class)
    })
    protected List<Object> messageIdAndStatus;

    /**
     * Gets the value of the messageIdAndStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageIdAndStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageIdAndStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link AcknowledgeStatus }
     * 
     * 
     */
    public List<Object> getMessageIdAndStatus() {
        if (messageIdAndStatus == null) {
            messageIdAndStatus = new ArrayList<Object>();
        }
        return this.messageIdAndStatus;
    }


}

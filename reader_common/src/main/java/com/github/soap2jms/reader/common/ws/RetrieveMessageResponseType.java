
package com.github.soap2jms.reader.common.ws;

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
 *         &lt;element name="s2jMessageAndStatus" type="{http://soap2jms.github.com/reader/common/ws}s2jMessageAndStatus" maxOccurs="unbounded" minOccurs="0"/>
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
    "s2JMessageAndStatus",
    "complete"
})
public class RetrieveMessageResponseType {

    @XmlElement(name = "s2jMessageAndStatus")
    protected List<S2JMessageAndStatus> s2JMessageAndStatus;
    protected boolean complete;

    /**
     * Gets the value of the s2JMessageAndStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the s2JMessageAndStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getS2JMessageAndStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link S2JMessageAndStatus }
     * 
     * 
     */
    public List<S2JMessageAndStatus> getS2JMessageAndStatus() {
        if (s2JMessageAndStatus == null) {
            s2JMessageAndStatus = new ArrayList<S2JMessageAndStatus>();
        }
        return this.s2JMessageAndStatus;
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

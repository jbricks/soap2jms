
package com.github.soap2jms.reader.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://soap2jms.github.com/reader/common/ws}s2jmsException"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "s2JmsException"
})
@XmlRootElement(name = "s2jmsConfiguratonException")
public class S2JmsConfiguratonException {

    @XmlElement(name = "s2jmsException", namespace = "http://soap2jms.github.com/reader/common/ws", required = true)
    protected S2JmsExceptionType s2JmsException;

    /**
     * Gets the value of the s2JmsException property.
     * 
     * @return
     *     possible object is
     *     {@link S2JmsExceptionType }
     *     
     */
    public S2JmsExceptionType getS2JmsException() {
        return s2JmsException;
    }

    /**
     * Sets the value of the s2JmsException property.
     * 
     * @param value
     *     allowed object is
     *     {@link S2JmsExceptionType }
     *     
     */
    public void setS2JmsException(S2JmsExceptionType value) {
        this.s2JmsException = value;
    }

}

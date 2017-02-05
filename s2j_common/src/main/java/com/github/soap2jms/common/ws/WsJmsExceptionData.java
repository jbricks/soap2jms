
package com.github.soap2jms.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.github.soap2jms.common.StatusCodeEnum;


/**
 * <p>Java class for wsJmsExceptionData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsJmsExceptionData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="originalException" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jmsCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="ERR_GENERIC"/>
 *             &lt;enumeration value="ERR_INCOMPATIBLE_PROTOCOL"/>
 *             &lt;enumeration value="ERR_JMS"/>
 *             &lt;enumeration value="ERR_MALFORMED_URL"/>
 *             &lt;enumeration value="ERR_NETWORK"/>
 *             &lt;enumeration value="ERR_QUEUE_NOT_FOUND"/>
 *             &lt;enumeration value="ERR_SERIALIZATION"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsJmsExceptionData", propOrder = {
    "reason",
    "originalException",
    "jmsCode"
})
public class WsJmsExceptionData {

    @XmlElement(required = true)
    protected String reason;
    protected String originalException;
    protected String jmsCode;
    @XmlAttribute(name = "code")
    protected String code;

    
	public WsJmsExceptionData() {

	}

	public WsJmsExceptionData(final StatusCodeEnum code, final String jmsCode,
			final String originalException, final String reason) {
		this.code = code == null ? StatusCodeEnum.ERR_GENERIC.name() : code.name();
		this.jmsCode = jmsCode;
		this.originalException = originalException;
		this.reason = reason;
	}
    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the originalException property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalException() {
        return originalException;
    }

    /**
     * Sets the value of the originalException property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalException(String value) {
        this.originalException = value;
    }

    /**
     * Gets the value of the jmsCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJmsCode() {
        return jmsCode;
    }

    /**
     * Sets the value of the jmsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJmsCode(String value) {
        this.jmsCode = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

}

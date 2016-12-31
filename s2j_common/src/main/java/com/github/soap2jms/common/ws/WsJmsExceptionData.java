
package com.github.soap2jms.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.WsExceptionClass;

/**
 * <p>
 * Java class for wsJmsExceptionData complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
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
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="exceptionClass">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="JMS"/>
 *             &lt;enumeration value="CONFIGURATION"/>
 *             &lt;enumeration value="OTHER"/>
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
@XmlType(name = "wsJmsExceptionData", propOrder = { "reason", "originalException", "jmsCode" })
public class WsJmsExceptionData {

	@XmlAttribute(name = "code")
	protected String code;
	@XmlAttribute(name = "exceptionClass")
	protected String exceptionClass;
	protected String jmsCode;
	protected String originalException;
	@XmlElement(required = true)
	protected String reason;

	public WsJmsExceptionData() {

	}

	public WsJmsExceptionData(final StatusCodeEnum code, WsExceptionClass exceptionClass, String jmsCode, String originalException,
			String reason) {
		this.code = code == null? StatusCodeEnum.ERR_GENERIC.name() : code.name();
		this.exceptionClass = exceptionClass == null ? WsExceptionClass.OTHER.name() : exceptionClass.name();
		this.jmsCode = jmsCode;
		this.originalException = originalException;
		this.reason = reason;
	}

	/**
	 * Gets the value of the code property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Gets the value of the exceptionClass property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getExceptionClass() {
		return this.exceptionClass;
	}

	/**
	 * Gets the value of the jmsCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getJmsCode() {
		return this.jmsCode;
	}

	/**
	 * Gets the value of the originalException property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOriginalException() {
		return this.originalException;
	}

	/**
	 * Gets the value of the reason property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@Deprecated
	public String getReason() {
		return this.reason;
	}

	/**
	 * Sets the value of the code property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCode(final String value) {
		this.code = value;
	}

	/**
	 * Sets the value of the exceptionClass property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setExceptionClass(final String value) {
		this.exceptionClass = value;
	}

	/**
	 * Sets the value of the jmsCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setJmsCode(final String value) {
		this.jmsCode = value;
	}

	/**
	 * Sets the value of the originalException property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOriginalException(final String value) {
		this.originalException = value;
	}

	/**
	 * Sets the value of the reason property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReason(final String value) {
		this.reason = value;
	}

}

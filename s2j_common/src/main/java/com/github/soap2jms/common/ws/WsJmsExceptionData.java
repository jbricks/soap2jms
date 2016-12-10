
package com.github.soap2jms.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

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
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="originalException" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="jmsCode" type="{http://www.w3.org/2001/XMLSchema}int" />
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
@XmlType(name = "wsJmsExceptionData", propOrder = { "message", "originalException" })
public class WsJmsExceptionData {

	@XmlAttribute(name = "code")
	protected Integer code;
	@XmlAttribute(name = "exceptionClass")
	protected String exceptionClass;
	@XmlAttribute(name = "jmsCode")
	protected Integer jmsCode;
	@XmlElement(required = true)
	protected String message;
	@XmlElement(required = true)
	protected String originalException;

	public WsJmsExceptionData() {
	}

	public WsJmsExceptionData(final String message, final String originalException, final Integer code,
			final Integer jmsCode, final WsExceptionClass exceptionType) {
		this.message = message;
		this.originalException = originalException;
		this.code = code;
		this.jmsCode = jmsCode;
		this.exceptionClass = exceptionType == null ? WsExceptionClass.OTHER.name() : exceptionType.name();
	}

	/**
	 * Gets the value of the jmsCode property.
	 *
	 * @return possible object is {@link Integer }
	 *
	 */
	public Integer getJmsCode() {
		return this.jmsCode;
	}

	/**
	 * Gets the value of the message property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getMessage() {
		return this.message;
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
	 * Sets the value of the code property.
	 *
	 * @param value
	 *            allowed object is {@link Integer }
	 *
	 */
	public void setCode(final Integer value) {
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
	 *            allowed object is {@link Integer }
	 *
	 */
	public void setJmsCode(final Integer value) {
		this.jmsCode = value;
	}

	/**
	 * Sets the value of the message property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setMessage(final String value) {
		this.message = value;
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

}

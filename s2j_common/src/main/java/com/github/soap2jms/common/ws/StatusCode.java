
package com.github.soap2jms.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.github.soap2jms.common.StatusCodeEnum;

/**
 * <p>
 * Java class for statusCode complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="statusCode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="ERR_GENERIC"/>
 *               &lt;enumeration value="ERR_INCOMPATIBLE_PROTOCOL"/>
 *               &lt;enumeration value="ERR_JMS"/>
 *               &lt;enumeration value="OK"/>
 *               &lt;enumeration value="WARN_MSG_NOT_FOUND"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="jmsCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statusCode", propOrder = { "code", "jmsCode", "reason" })
public class StatusCode {

	public static final StatusCode STATUS_OK = new StatusCode(StatusCodeEnum.OK, null, null);

	@XmlElement(required = true)
	protected String code;
	protected String jmsCode;
	protected String reason;

	public StatusCode() {
	}

	public StatusCode(String code, String jmsCode, String reason) {
		this.code = code;
		this.jmsCode = jmsCode;
		this.reason = reason;
	}

	public StatusCode(StatusCodeEnum code, String jmsCode, String reason) {
		this.code = code.name();
		this.jmsCode = jmsCode;
		this.reason = reason;
	}

	/**
	 * Gets the value of the code property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCode(String value) {
		this.code = value;
	}

	/**
	 * Gets the value of the jmsCode property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public String getJmsCode() {
		return jmsCode;
	}

	/**
	 * Sets the value of the jmsCode property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setJmsCode(String value) {
		this.jmsCode = value;
	}

	/**
	 * Gets the value of the reason property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Sets the value of the reason property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReason(String value) {
		this.reason = value;
	}

}

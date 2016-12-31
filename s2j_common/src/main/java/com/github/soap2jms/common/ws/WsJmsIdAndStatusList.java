
package com.github.soap2jms.common.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for wsJmsIdAndStatusList complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="wsJmsIdAndStatusList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageIdAndStatus" type="{http://soap2jms.github.com/common/ws}messageIdAndStatus" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsJmsIdAndStatusList", propOrder = { "messageIdAndStatus" })
public class WsJmsIdAndStatusList {

	@XmlElement(required = true)
	protected List<MessageIdAndStatus> messageIdAndStatus;

	/**
	 * Gets the value of the messageIdAndStatus property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the messageIdAndStatus property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getMessageIdAndStatus().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link MessageIdAndStatus }
	 *
	 *
	 */
	public List<MessageIdAndStatus> getMessageIdAndStatus() {
		if (this.messageIdAndStatus == null) {
			this.messageIdAndStatus = new ArrayList<>();
		}
		return this.messageIdAndStatus;
	}

}

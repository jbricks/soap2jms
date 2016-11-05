
package com.github.soap2jms.reader.common.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.soap2jms.reader.common.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WsJmsException_QNAME = new QName("http://soap2jms.github.com/reader/common/ws", "wsJmsException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.soap2jms.reader.common.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WsJmsMessage }
     * 
     */
    public WsJmsMessage createWsJmsMessage() {
        return new WsJmsMessage();
    }

    /**
     * Create an instance of {@link AcknowledgeMessagesResponse }
     * 
     */
    public AcknowledgeMessagesResponse createAcknowledgeMessagesResponse() {
        return new AcknowledgeMessagesResponse();
    }

    /**
     * Create an instance of {@link MessageIdAndStatus }
     * 
     */
    public MessageIdAndStatus createMessageIdAndStatus() {
        return new MessageIdAndStatus();
    }

    /**
     * Create an instance of {@link WsJmsExceptionData }
     * 
     */
    public WsJmsExceptionData createWsJmsExceptionData() {
        return new WsJmsExceptionData();
    }

    /**
     * Create an instance of {@link RetrieveMessages }
     * 
     */
    public RetrieveMessages createRetrieveMessages() {
        return new RetrieveMessages();
    }

    /**
     * Create an instance of {@link RetrieveMessagesResponse }
     * 
     */
    public RetrieveMessagesResponse createRetrieveMessagesResponse() {
        return new RetrieveMessagesResponse();
    }

    /**
     * Create an instance of {@link RetrieveMessageResponseType }
     * 
     */
    public RetrieveMessageResponseType createRetrieveMessageResponseType() {
        return new RetrieveMessageResponseType();
    }

    /**
     * Create an instance of {@link AcknowledgeMessages }
     * 
     */
    public AcknowledgeMessages createAcknowledgeMessages() {
        return new AcknowledgeMessages();
    }

    /**
     * Create an instance of {@link ResponseStatus }
     * 
     */
    public ResponseStatus createResponseStatus() {
        return new ResponseStatus();
    }

    /**
     * Create an instance of {@link WsJmsMessageAndStatus }
     * 
     */
    public WsJmsMessageAndStatus createWsJmsMessageAndStatus() {
        return new WsJmsMessageAndStatus();
    }

    /**
     * Create an instance of {@link WsJmsMessage.Headers }
     * 
     */
    public WsJmsMessage.Headers createWsJmsMessageHeaders() {
        return new WsJmsMessage.Headers();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsJmsExceptionData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap2jms.github.com/reader/common/ws", name = "wsJmsException")
    public JAXBElement<WsJmsExceptionData> createWsJmsException(WsJmsExceptionData value) {
        return new JAXBElement<WsJmsExceptionData>(_WsJmsException_QNAME, WsJmsExceptionData.class, null, value);
    }

}


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

    private final static QName _S2JmsException_QNAME = new QName("http://soap2jms.github.com/reader/common/ws", "s2jmsException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.soap2jms.reader.common.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link S2JMessage }
     * 
     */
    public S2JMessage createS2JMessage() {
        return new S2JMessage();
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
     * Create an instance of {@link S2JmsExceptionType }
     * 
     */
    public S2JmsExceptionType createS2JmsExceptionType() {
        return new S2JmsExceptionType();
    }

    /**
     * Create an instance of {@link S2JmsConfiguratonException }
     * 
     */
    public S2JmsConfiguratonException createS2JmsConfiguratonException() {
        return new S2JmsConfiguratonException();
    }

    /**
     * Create an instance of {@link S2JMessageAndStatus }
     * 
     */
    public S2JMessageAndStatus createS2JMessageAndStatus() {
        return new S2JMessageAndStatus();
    }

    /**
     * Create an instance of {@link ResponseStatus }
     * 
     */
    public ResponseStatus createResponseStatus() {
        return new ResponseStatus();
    }

    /**
     * Create an instance of {@link S2JMessage.Headers }
     * 
     */
    public S2JMessage.Headers createS2JMessageHeaders() {
        return new S2JMessage.Headers();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S2JmsExceptionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap2jms.github.com/reader/common/ws", name = "s2jmsException")
    public JAXBElement<S2JmsExceptionType> createS2JmsException(S2JmsExceptionType value) {
        return new JAXBElement<S2JmsExceptionType>(_S2JmsException_QNAME, S2JmsExceptionType.class, null, value);
    }

}

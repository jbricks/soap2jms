
package com.github.soap2jms.reader_common;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.soap2jms.reader_common package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.soap2jms.reader_common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JmsMessage }
     * 
     */
    public JmsMessage createJmsMessage() {
        return new JmsMessage();
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
     * Create an instance of {@link JmsMessageAndStatus }
     * 
     */
    public JmsMessageAndStatus createJmsMessageAndStatus() {
        return new JmsMessageAndStatus();
    }

    /**
     * Create an instance of {@link ResponseStatus }
     * 
     */
    public ResponseStatus createResponseStatus() {
        return new ResponseStatus();
    }

    /**
     * Create an instance of {@link JmsMessage.Headers }
     * 
     */
    public JmsMessage.Headers createJmsMessageHeaders() {
        return new JmsMessage.Headers();
    }

}

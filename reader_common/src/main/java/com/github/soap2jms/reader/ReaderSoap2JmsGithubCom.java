package com.github.soap2jms.reader;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(targetNamespace = "http://soap2jms.github.com/reader", name = "reader.soap2jms.github.com")
@XmlSeeAlso({com.github.soap2jms.reader_common.ObjectFactory.class})
public interface ReaderSoap2JmsGithubCom {

    @WebResult(name = "retrieveMessagesResponse", targetNamespace = "")
    @RequestWrapper(localName = "retrieveMessages", targetNamespace = "http://soap2jms.github.com/reader_common", className = "com.github.soap2jms.reader_common.RetrieveMessages")
    @WebMethod(action = "http://soap2jms.github.com/retrieveMessages")
    @ResponseWrapper(localName = "retrieveMessagesResponse", targetNamespace = "http://soap2jms.github.com/reader_common", className = "com.github.soap2jms.reader_common.RetrieveMessagesResponse")
    public com.github.soap2jms.reader_common.RetrieveMessageResponseType retrieveMessages(
        @WebParam(name = "queueName", targetNamespace = "")
        java.lang.String queueName,
        @WebParam(name = "filter", targetNamespace = "")
        java.lang.String filter,
        @WebParam(name = "maxItems", targetNamespace = "")
        int maxItems
    );


    @WebResult(name = "messageIdAndStatus", targetNamespace = "")
    @RequestWrapper(localName = "acknowledgeMessages", targetNamespace = "http://soap2jms.github.com/reader_common", className = "com.github.soap2jms.reader_common.AcknowledgeMessages")
    @WebMethod(action = "http://soap2jms.github.com/acknowledgeMessages")
    @ResponseWrapper(localName = "acknowledgeMessagesResponse", targetNamespace = "http://soap2jms.github.com/reader_common", className = "com.github.soap2jms.reader_common.AcknowledgeMessagesResponse")
    public java.util.List<com.github.soap2jms.reader_common.MessageIdAndStatus> acknowledgeMessages(
        @WebParam(name = "messageId", targetNamespace = "")
        java.util.List<java.lang.String> messageId
    );
}
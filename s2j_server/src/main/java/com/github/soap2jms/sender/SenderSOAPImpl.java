package com.github.soap2jms.sender;

import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.common.serialization.JMSMessageFactory;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.queue.IdAndStatus;
import com.github.soap2jms.queue.QueueInspector;
import com.github.soap2jms.reader.ReaderSOAPImpl;
import com.github.soap2jms.service.SenderSoap2Jms;
import com.github.soap2jms.service.WsJmsException;

@javax.jws.WebService(serviceName = "jmsReaderSoap", portName = "senderSOAP", targetNamespace = "http://soap2jms.github.com/service")
public class SenderSOAPImpl implements SenderSoap2Jms {
	private static final Logger LOG = LoggerFactory.getLogger(ReaderSOAPImpl.class);
	@Inject
	private QueueInspector qi;
	@Inject
	private SoapToJmsSerializer serializationUtils;

	public SenderSOAPImpl() {

	}

	@Override
	public List<MessageIdAndStatus> sendMessages(String queueName, String messageSetIdentifier,
			List<WsJmsMessage> wsMessages) throws WsJmsException {

		JMSMessageFactory jmsMessageFactory = qi.getJmsMessageFactory();
		Message[] jmsMessages = serializationUtils.convertMessages(jmsMessageFactory, wsMessages);
		IdAndStatus[] result = null;
		try {
			result = qi.sendMessages(queueName, jmsMessages);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serializationUtils.idAndStatusToWS(result);
	}

}

package com.github.soap2jms.sender;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.WsExceptionClass;
import com.github.soap2jms.common.serialization.JMSMessageFactory;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.queue.IdAndStatus;
import com.github.soap2jms.queue.QueueInspector;
import com.github.soap2jms.reader.ReaderSOAPImpl;
import com.github.soap2jms.service.SenderSoap2Jms;
import com.github.soap2jms.service.WsJmsException;

@javax.jws.WebService(serviceName = "soapToJmsSenderService", portName = "senderSOAP", targetNamespace = "http://soap2jms.github.com/service")
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
		Message[] jmsMessages = new Message[wsMessages.size()];
		for (int i = 0; i < wsMessages.size(); i++) {
			jmsMessages[i] = serializationUtils.convertMessage(jmsMessageFactory, wsMessages.get(i));
		}
		IdAndStatus[] result = null;
		try {
			result = qi.sendMessages(queueName, jmsMessages);
		} catch (JMSException e) {
			throw new WsJmsException("JMS problem. Queue " + queueName, e.getMessage(), StatusCodeEnum.ERR_JMS,
					WsExceptionClass.JMS);
		} catch (NamingException e) {
			throw new WsJmsException("JMS problem. Queue " + queueName, e.getMessage(), StatusCodeEnum.ERR_JMS,
					WsExceptionClass.CONFIGURATION);
		}
		return idAndStatusToWS(result);
	}

	private List<MessageIdAndStatus> idAndStatusToWS(IdAndStatus[] results) {
		List<MessageIdAndStatus> messageIdAndStatusList = new ArrayList<>();
		for (IdAndStatus result : results) {
			final MessageIdAndStatus messageIdAndStatus1 = new MessageIdAndStatus(result.getMessageId(),
					result.getStatusCode(), result.getReason());
			messageIdAndStatusList.add(messageIdAndStatus1);
		}
		return messageIdAndStatusList;
	}

}

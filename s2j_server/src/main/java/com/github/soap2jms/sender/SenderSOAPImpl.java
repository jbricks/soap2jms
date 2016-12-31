package com.github.soap2jms.sender;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.common.S2JConfigurationException;
import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.S2JProviderException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.WsExceptionClass;
import com.github.soap2jms.common.serialization.JMSImplementation;
import com.github.soap2jms.common.serialization.JMSMessageFactory;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.queue.IdAndStatus;
import com.github.soap2jms.queue.QueueSender;
import com.github.soap2jms.reader.ReaderSOAPImpl;
import com.github.soap2jms.service.SenderSoap2Jms;
import com.github.soap2jms.service.WsJmsException;

@javax.jws.WebService(serviceName = "soapToJmsSenderService", portName = "senderSOAP", targetNamespace = "http://soap2jms.github.com/service")
public class SenderSOAPImpl implements SenderSoap2Jms {
	private static final Logger LOG = LoggerFactory.getLogger(ReaderSOAPImpl.class);

	@Inject
	QueueSender qs;
	@Inject
	private SoapToJmsSerializer serializationUtils;

	public SenderSOAPImpl() {
	}

	private List<MessageIdAndStatus> idAndStatusToWS(final IdAndStatus[] results) {
		final List<MessageIdAndStatus> messageIdAndStatusList = new ArrayList<>();
		for (final IdAndStatus result : results) {
			final MessageIdAndStatus messageIdAndStatus1 = new MessageIdAndStatus(null, result.getStatusCode(),
					result.getReason(), result.getJmsCode());
			messageIdAndStatusList.add(messageIdAndStatus1);
		}
		return messageIdAndStatusList;
	}

	@Override
	public List<MessageIdAndStatus> sendMessages(final String clientIdentifier, final String queueName,
			final List<WsJmsMessage> wsMessages) throws WsJmsException {

		final Message[] jmsMessages = new Message[wsMessages.size()];
		final IdAndStatus[] result = new IdAndStatus[wsMessages.size()];
		try {
			this.qs.open(queueName);
			final JMSMessageFactory jmsMessageFactory = qs.getJmsMessageFactory();
			for (int i = 0; i < wsMessages.size(); i++) {
				try {
					jmsMessages[i] = this.serializationUtils.convertMessage(jmsMessageFactory, wsMessages.get(i),
							JMSImplementation.ARTEMIS_ACTIVE_MQ);
				} catch (S2JProviderException e) {
					LOG.error("Error deserializing message from webservice", e);
					result[i] = new IdAndStatus(e.getStatusCode(), e.getJmsCode(), e.getMessage());
					jmsMessages[i] = null;
				}
			}
			for (int i = 0; i < wsMessages.size(); i++) {
				if (jmsMessages[i] != null) {
					result[i] = qs.sendMessage(jmsMessages[i]);
				}
			}
		} catch (S2JConfigurationException e1) {
			LOG.error("Error sending messages to " + queueName + ". ", e1);
			throw new WsJmsException(e1.getMessage(), e1.toString(), e1.getStatusCode(), null,
					WsExceptionClass.CONFIGURATION);
		} catch (S2JProtocolException e1) {
			LOG.error("Error sending messages to " + queueName + ". ", e1);
			throw new WsJmsException(e1.getMessage(), e1.toString(), e1.getStatusCode(), null,
					WsExceptionClass.PROTOCOL);
		} catch (S2JProviderException e1) {
			LOG.error("Error sending messages to " + queueName + ". ", e1);
			throw new WsJmsException(e1.getMessage(), e1.getCause().toString(), e1.getStatusCode(), e1.getJmsCode(),
					WsExceptionClass.OTHER);
		} catch (Exception e1) {
			LOG.error("Error sending messages to " + queueName + ". ", e1);
			throw new WsJmsException(e1.getMessage(), e1.toString(), StatusCodeEnum.ERR_GENERIC, null,
					WsExceptionClass.OTHER);
		} finally {
			qs.close();
		}
		return idAndStatusToWS(result);
	}

}

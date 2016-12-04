package com.github.soap2jms.pub;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.serialization.JmsToSoapSerializer;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.model.ClientMessageFactory;
import com.github.soap2jms.model.InternalServerException;
import com.github.soap2jms.model.NetworkException;
import com.github.soap2jms.model.S2JConfigurationException;
import com.github.soap2jms.model.S2JMessage;
import com.github.soap2jms.service.ReaderSoap2Jms;
import com.github.soap2jms.service.SenderSoap2Jms;
import com.github.soap2jms.service.SoapToJmsReaderService;
import com.github.soap2jms.service.SoapToJmsSenderService;
import com.github.soap2jms.service.WsJmsException;

public class SoapToJmsClient {
	private final SenderSoap2Jms senderSoapPort;
	private final ReaderSoap2Jms readerSoapPort;
	private final SoapToJmsSerializer soap2JmsSerializer = new SoapToJmsSerializer();
	private final ThreadLocal<Boolean> isComplete = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.TRUE;
		}
	};

	public SoapToJmsClient(final SoapToJmsReaderService readerService, SoapToJmsSenderService senderService) {
		this.readerSoapPort = readerService == null ? null : readerService.getReaderSOAP();
		this.senderSoapPort = senderService == null ? null : senderService.getSenderSOAP();
	}

	public SoapToJmsClient(final String contextBase) throws S2JConfigurationException {
		this(contextBase, true, true);
	}

	public SoapToJmsClient(final String contextBase, boolean initReader, boolean initSender)
			throws S2JConfigurationException {
		String contextBaseNorm = contextBase.endsWith("/") ? contextBase : contextBase + "/";
		String senderUrl = contextBase + "soapToJmsSenderService?wsdl";
		final SoapToJmsReaderService readerService;
		if (initReader) {
			String readerUrl = contextBase + "soapToJmsReaderService?wsdl";
			try {
				SoapToJmsReaderService soap2JmsService = new SoapToJmsReaderService(readerUrl);
				this.readerSoapPort = soap2JmsService.getReaderSOAP();
			} catch (final MalformedURLException e) {
				throw new S2JConfigurationException("Url " + readerUrl + " is malformed.", e);
			}
		} else {
			this.readerSoapPort = null;
		}
		if (initSender) {
			String readerUrl = contextBase + "soapToJmsSenderService?wsdl";
			try {
				SoapToJmsSenderService soap2JmsService = new SoapToJmsSenderService(readerUrl);
				this.senderSoapPort = soap2JmsService.getSenderSOAP();
			} catch (final MalformedURLException e) {
				throw new S2JConfigurationException("Url " + readerUrl + " is malformed.", e);
			}
		} else {
			this.senderSoapPort = null;
		}
	}

	public void acknolwedge(String queueName, final List<String> messageIds) throws InternalServerException {
		try {
			readerSoapPort.acknowledgeMessages(queueName, messageIds);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
	}

	public void acknowledge(String queueName, final Message[] messages)
			throws S2JProtocolException, NetworkException, InternalServerException, JMSException {
		List<String> msgIds = new ArrayList<>(messages.length);
		for (Message message : messages) {
			msgIds.add(message.getJMSMessageID());
		}
		acknolwedge(queueName, msgIds);
	}

	public boolean retrieveComplete() {
		return isComplete.get();
	}

	public Message[] readMessages(final String queueName, final String filter, final int msgMax)
			throws S2JProtocolException, NetworkException, InternalServerException {

		RetrieveMessageResponseType wsResponse;
		try {
			wsResponse = readerSoapPort.retrieveMessages(queueName, filter, msgMax);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
		this.isComplete.set(wsResponse.isComplete());
		return soap2JmsSerializer.convertMessages(new ClientMessageFactory(), wsResponse.getS2JMessageAndStatus());
	}

	public void sendMessages(final String queueName, final S2JMessage[] messages)
			throws S2JProtocolException, NetworkException, InternalServerException {

		List<WsJmsMessage> messages1;
		try {
			messages1 = new JmsToSoapSerializer().messagesToWs(messages);
		} catch (JMSException e1) {
			throw new S2JProtocolException(StatusCodeEnum.ERR_SERIALIZATION, "", e1);
		}
		try {
			senderSoapPort.sendMessages(queueName, null, messages1);

		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
	}
}

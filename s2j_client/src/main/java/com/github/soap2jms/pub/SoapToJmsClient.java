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
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.model.ClientMessageFactory;
import com.github.soap2jms.model.InternalServerException;
import com.github.soap2jms.model.NetworkException;
import com.github.soap2jms.model.ResponseStatus;
import com.github.soap2jms.model.S2JConfigurationException;
import com.github.soap2jms.model.S2JMessage;
import com.github.soap2jms.service.ReaderSoap2Jms;
import com.github.soap2jms.service.SenderSoap2Jms;
import com.github.soap2jms.service.SoapToJmsReaderService;
import com.github.soap2jms.service.SoapToJmsSenderService;
import com.github.soap2jms.service.WsJmsException;

public class SoapToJmsClient {
	private final ThreadLocal<Boolean> isComplete = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.TRUE;
		}
	};
	private final ReaderSoap2Jms readerSoapPort;
	private final SenderSoap2Jms senderSoapPort;
	private final SoapToJmsSerializer soap2JmsSerializer = new SoapToJmsSerializer();

	public SoapToJmsClient(final SoapToJmsReaderService readerService, final SoapToJmsSenderService senderService) {
		this.readerSoapPort = readerService == null ? null : readerService.getReaderSOAP();
		this.senderSoapPort = senderService == null ? null : senderService.getSenderSOAP();
	}

	public SoapToJmsClient(final String contextBase) throws S2JConfigurationException {
		this(contextBase, true, true);
	}

	public SoapToJmsClient(final String contextBase, final boolean initReader, final boolean initSender)
			throws S2JConfigurationException {
		final String contextBaseNorm = contextBase.endsWith("/") ? contextBase : contextBase + "/";
		if (initReader) {
			final String readerUrl = contextBaseNorm + "soapToJmsReaderService?wsdl";
			try {
				final SoapToJmsReaderService soap2JmsService = new SoapToJmsReaderService(readerUrl);
				this.readerSoapPort = soap2JmsService.getReaderSOAP();
			} catch (final MalformedURLException e) {
				throw new S2JConfigurationException("Url " + readerUrl + " is malformed.", e);
			}
		} else {
			this.readerSoapPort = null;
		}
		if (initSender) {
			final String senderUrl = contextBaseNorm + "soapToJmsSenderService?wsdl";
			try {
				final SoapToJmsSenderService soap2JmsService = new SoapToJmsSenderService(senderUrl);
				this.senderSoapPort = soap2JmsService.getSenderSOAP();
			} catch (final MalformedURLException e) {
				throw new S2JConfigurationException("Url " + senderUrl + " is malformed.", e);
			}
		} else {
			this.senderSoapPort = null;
		}
	}

	public ResponseStatus acknolwedge(final String queueName, final List<String> messageIds)
			throws InternalServerException {
		List<MessageIdAndStatus> result;
		try {
			result = this.readerSoapPort.acknowledgeMessages(queueName, messageIds);
		} catch (final WsJmsException e) {
			throw new InternalServerException("", e);
		}
		return new ResponseStatus(result);
	}

	public ResponseStatus acknowledge(final String queueName, final Message[] messages)
			throws S2JProtocolException, NetworkException, InternalServerException, JMSException {
		final List<String> msgIds = new ArrayList<>(messages.length);
		for (final Message message : messages) {
			msgIds.add(message.getJMSMessageID());
		}
		return acknolwedge(queueName, msgIds);
	}

	public Message[] readMessages(final String queueName, final String filter, final int msgMax)
			throws S2JProtocolException, NetworkException, InternalServerException {

		RetrieveMessageResponseType wsResponse;
		try {
			wsResponse = this.readerSoapPort.retrieveMessages(queueName, filter, msgMax);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
		this.isComplete.set(wsResponse.isComplete());
		return this.soap2JmsSerializer.convertMessages(new ClientMessageFactory(), wsResponse.getS2JMessageAndStatus());
	}

	public boolean retrieveComplete() {
		return this.isComplete.get();
	}

	public ResponseStatus sendMessages(final String queueName, final S2JMessage[] messages)
			throws S2JProtocolException, NetworkException, InternalServerException {

		List<WsJmsMessage> messages1;
		try {
			messages1 = new JmsToSoapSerializer().messagesToWs(messages);
		} catch (final JMSException e1) {
			throw new S2JProtocolException(StatusCodeEnum.ERR_SERIALIZATION, "", e1);
		}
		List<MessageIdAndStatus> result;
		try {
			result = this.senderSoapPort.sendMessages(queueName, null, messages1);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
		return new ResponseStatus(result);
	}
}

package com.github.soap2jms.pub;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.serialization.JMSImplementation;
import com.github.soap2jms.common.serialization.JmsToSoapSerializer;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.model.ClientMessageFactory;
import com.github.soap2jms.model.S2JServerException;
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
	private final SoapToJmsSerializer soap2JmsSerializer;
	private final ClientSerializationUtils jms2SoapSerializer;

	public SoapToJmsClient(final SoapToJmsReaderService readerService, final SoapToJmsSenderService senderService,
			SoapToJmsSerializer s2jser, JmsToSoapSerializer jms2SoapSerializer) {
		this.readerSoapPort = readerService == null ? null : readerService.getReaderSOAP();
		this.senderSoapPort = senderService == null ? null : senderService.getSenderSOAP();
		this.soap2JmsSerializer = s2jser;
		this.jms2SoapSerializer = new ClientSerializationUtils();
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
		this.soap2JmsSerializer = new SoapToJmsSerializer();
		this.jms2SoapSerializer = new ClientSerializationUtils();
	}

	public ResponseStatus acknolwedge(final String queueName, final List<String> messageIds) throws S2JServerException {
		List<MessageIdAndStatus> result;
		try {
			result = this.readerSoapPort.acknowledgeMessages(queueName, messageIds);
		} catch (final WsJmsException e) {
			throw new S2JServerException("", e);
		}
		return new ResponseStatus(result);
	}

	public ResponseStatus acknowledge(final String queueName, final Message[] messages)
			throws S2JProtocolException, NetworkException, S2JServerException, JMSException {
		final List<String> msgIds = new ArrayList<>(messages.length);
		for (final Message message : messages) {
			msgIds.add(message.getJMSMessageID());
		}
		return acknolwedge(queueName, msgIds);
	}

	public Message[] readMessages(final String queueName, final String filter, final int msgMax)
			throws S2JProtocolException, NetworkException, S2JServerException {

		RetrieveMessageResponseType wsResponse;
		try {
			wsResponse = this.readerSoapPort.retrieveMessages(queueName, filter, msgMax);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new S2JServerException("", e);
		}
		this.isComplete.set(wsResponse.isComplete());
		return this.soap2JmsSerializer.convertMessages(new ClientMessageFactory(), wsResponse.getS2JMessageAndStatus(),
				JMSImplementation.NONE);
	}

	public boolean retrieveComplete() {
		return this.isComplete.get();
	}

	/**
	 * 
	 * @param queueName
	 * @param messages
	 * @return
	 * @throws S2JProtocolException
	 *             This exception is threw when client and server are not the
	 *             same version or some error marshalling/unmarshalling
	 *             parameters happened (messages has not been delivered). This
	 *             exception is permanent. When received all the messages should
	 *             be placed in error state.
	 * @throws S2JConfigurationException
	 *             This exception is threw when client parameters are not
	 *             correct (the queue doesn't exist...). This exception is
	 *             permanent. Don't retry to send messages, unless the
	 *             parameters are changed.
	 */
	public ResponseStatus sendMessages(final String queueName, final S2JMessage[] messages)
			throws S2JProtocolException, S2JConfigurationException {

		List<WsJmsMessage> messages1;
		try {
			messages1 = jms2SoapSerializer.messagesToWs(messages);
		} catch (final JMSException e1) {
			throw new S2JProtocolException(StatusCodeEnum.ERR_SERIALIZATION, "", e1);
		}
		List<MessageIdAndStatus> result;
		try {
			result = this.senderSoapPort.sendMessages(null, queueName, messages1);
		} catch (final WsJmsException e) {

			/*
			 * S2JServerException This exception may be temporary, it may be
			 * possible to resend messages.
			 */
			// FIXME: error handling.
			throw new S2JProtocolException(StatusCodeEnum.ERR_GENERIC);
		}
		return new ResponseStatus(result);
	}


}

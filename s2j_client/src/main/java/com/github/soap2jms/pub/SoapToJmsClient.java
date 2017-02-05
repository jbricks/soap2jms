package com.github.soap2jms.pub;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.common.S2JConfigurationException;
import com.github.soap2jms.common.S2JException;
import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.S2JProviderException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.serialization.JMSImplementation;
import com.github.soap2jms.common.serialization.JMSMessageFactory;
import com.github.soap2jms.common.serialization.JmsToSoapSerializer;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.StatusCode;
import com.github.soap2jms.common.ws.WsJmsExceptionData;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.model.ClientMessageFactory;
import com.github.soap2jms.model.MessageDeliveryStatus;
import com.github.soap2jms.model.ResponseStatus;
import com.github.soap2jms.model.S2JMessage;
import com.github.soap2jms.service.ReaderSoap2Jms;
import com.github.soap2jms.service.SenderSoap2Jms;
import com.github.soap2jms.service.SoapToJmsReaderService;
import com.github.soap2jms.service.SoapToJmsSenderService;
import com.github.soap2jms.service.WsJmsException;

/**
 * This is the main class that should be used to send or read messages from a
 * remote queue.
 *
 *
 * <p>
 * Methods are reentrant, a single instance can be shared by multiple threads
 * </p>
 *
 * @author g.contini
 *
 */
public class SoapToJmsClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(SoapToJmsClient.class);
	private final SoapToJmsConfiguration configuration;
	private final ThreadLocal<Boolean> isComplete = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.TRUE;
		}
	};
	private final ClientSerializationUtils jms2SoapSerializer;
	private final ReaderSoap2Jms readerSoapPort;
	private final SenderSoap2Jms senderSoapPort;
	private final SoapToJmsSerializer soap2JmsSerializer;

	public SoapToJmsClient(final SoapToJmsConfiguration config) throws S2JConfigurationException {
		this(config, true, true);
	}

	public SoapToJmsClient(final SoapToJmsConfiguration config, final boolean initReader, final boolean initSender)
			throws S2JConfigurationException {
		this.configuration = config;
		final String contextBase = config.getContextUrl();
		final String contextBaseNorm = contextBase.endsWith("/") ? contextBase : contextBase + "/";
		if (initReader) {
			final String readerUrl = contextBaseNorm + "soapToJmsReaderService?wsdl";
			try {
				final SoapToJmsReaderService soap2JmsService = new SoapToJmsReaderService(readerUrl);
				this.readerSoapPort = soap2JmsService.getReaderSOAP();

				setTimeouts((BindingProvider) this.readerSoapPort);
			} catch (final MalformedURLException e) {
				throw new S2JConfigurationException(StatusCodeEnum.ERR_MALFORMED_URL,
						"Url " + readerUrl + " is malformed.", e);
			}
		} else {
			this.readerSoapPort = null;
		}
		if (initSender) {
			final String senderUrl = contextBaseNorm + "soapToJmsSenderService?wsdl";
			try {
				final SoapToJmsSenderService soap2JmsService = new SoapToJmsSenderService(senderUrl);
				this.senderSoapPort = soap2JmsService.getSenderSOAP();
				final BindingProvider spAsBindingProvider = (BindingProvider) this.senderSoapPort;
				setTimeouts(spAsBindingProvider);
			} catch (final MalformedURLException e) {
				throw new S2JConfigurationException(StatusCodeEnum.ERR_MALFORMED_URL,
						"Url " + senderUrl + " is malformed.", e);
			}
		} else {
			this.senderSoapPort = null;
		}
		this.soap2JmsSerializer = new SoapToJmsSerializer();
		this.jms2SoapSerializer = new ClientSerializationUtils();
	}

	public SoapToJmsClient(final SoapToJmsReaderService readerService, final SoapToJmsSenderService senderService,
			final SoapToJmsSerializer s2jser, final JmsToSoapSerializer jms2SoapSerializer) {

		this.readerSoapPort = readerService == null ? null : readerService.getReaderSOAP();
		this.senderSoapPort = senderService == null ? null : senderService.getSenderSOAP();
		// FIXME
		this.configuration = new SoapToJmsConfiguration(readerService.getWSDLDocumentLocation().toString());
		this.soap2JmsSerializer = s2jser;
		this.jms2SoapSerializer = new ClientSerializationUtils();
	}

	/**
	 * This is the simplest constructor to get an instance of the client.
	 * <p>
	 * It attempts a connection to the provided <code>contextUrl</code>.
	 * </p>
	 *
	 * @param contextUrl
	 *            the complete url of the web application where the server is
	 *            deployed.
	 *
	 * @throws S2JConfigurationException
	 *             This exception is threw when there is a problem in the
	 *             parameters, usually the contextUrl is wrong.
	 *
	 */
	public SoapToJmsClient(final String contextUrl) throws S2JConfigurationException {
		this(new SoapToJmsConfiguration(contextUrl));
	}

	/**
	 * 
	 * @param queueName
	 * @param messageIds
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
	 * @throws S2JProviderException
	 *             Server threw a JMSException.
	 * @throws S2JException
	 *             General exception used for an unexpected error condition.
	 */
	public ResponseStatus<String> acknolwedge(final String queueName, final List<String> messageIds)
			throws S2JException, S2JProtocolException, S2JConfigurationException, S2JProviderException {
		List<MessageIdAndStatus> wsResult = null;
		try {
			wsResult = this.readerSoapPort.acknowledgeMessages(queueName, messageIds);
		} catch (final WsJmsException e) {
			handleWsException(e);
			// we never arrive here
		}
		final ResponseStatus<String> result = convertAckResponse(messageIds, wsResult);
		return result;
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
	 * @throws S2JProviderException
	 *             Server threw a JMSException.
	 * @throws S2JException
	 *             General exception used for an unexpected error condition.
	 */
	public ResponseStatus<String> acknowledge(final String queueName, final Message[] messages)
			throws S2JException, S2JProtocolException, S2JConfigurationException, S2JProviderException {
		final List<String> msgIds = new ArrayList<>(messages.length);
		for (final Message message : messages) {
			if (message != null) {
				try {
					msgIds.add(message.getJMSMessageID());
				} catch (final JMSException e) {
					throw new S2JProviderException(StatusCodeEnum.ERR_JMS, "Error getting message id for " + message,
							e.getErrorCode(), e);
				}
			}
		}
		return acknolwedge(queueName, msgIds);
	}

	public Message[] convertMessages(final JMSMessageFactory messageFactory, final List<WsJmsMessage> wsResponse,
			final JMSImplementation jmsImplementation) throws S2JProtocolException {
		final Message[] messages = new Message[wsResponse.size()];

		for (int i = 0; i < wsResponse.size(); i++) {
			final WsJmsMessage wsMessage = wsResponse.get(i);
			Message message = null;
			try {
				message = this.soap2JmsSerializer.convertMessage(messageFactory, wsMessage, jmsImplementation);
			} catch (final S2JProviderException e) {
				LOGGER.error("Error deserializing message " + wsMessage + " message position:" + i + " messageClientId:"
						+ wsMessage.getClientId(), e);
			}
			messages[i] = message;
		}

		return messages;
	}

	/**
	 *
	 * @param queueName
	 * @param filter
	 * @param msgMax
	 * @return the returned message can be null in case of error.
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
	 * @throws S2JProviderException
	 *             Server threw a JMSException.
	 * @throws S2JException
	 *             General exception used for an unexpected error condition.
	 */
	public Message[] readMessages(final String queueName, final String filter, final int msgMax)
			throws S2JException, S2JProtocolException, S2JConfigurationException, S2JProviderException {

		RetrieveMessageResponseType wsResponse = null;
		try {
			wsResponse = this.readerSoapPort.retrieveMessages(queueName, filter, msgMax);
		} catch (final WsJmsException e) {
			handleWsException(e);
		}
		this.isComplete.set(wsResponse.isComplete());
		return convertMessages(new ClientMessageFactory(), wsResponse.getS2JMessages(), JMSImplementation.NONE);
	}

	public boolean retrieveComplete() {
		return this.isComplete.get();
	}

	/**
	 * <p>
	 * This method allow to send messages to a remote queue.
	 * </p>
	 *
	 * <p>
	 * A complete description on how to use this method is available on <a href=
	 * "https://jbricks.github.io/soap2jms/user_guide/client_usage.html">github
	 * pages</a>.
	 * </p>
	 * <p>
	 * Exception semantics. If exception is thrown means that:
	 * <ul>
	 * <li>The whole call failed. All the messages were not delivered.</li>
	 * <li>The service was called in a way that is not correct. Subsequent calls
	 * will result in an exception.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param queueName
	 *            name of the queue, as configured on the server. If "null" the
	 *            messages will be sent to the default queue on server.
	 * @param messages
	 *            An array of S2JMessage. Users should instantiate the subclass
	 *            corresponding to the type of the message they want to send.
	 * @return
	 *
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
	 * @throws S2JProviderException
	 *             Server threw a JMSException.
	 * @throws S2JException
	 *             General exception used for an unexpected error condition.
	 */
	public ResponseStatus<S2JMessage> sendMessages(final String queueName, final S2JMessage[] messages)
			throws S2JException, S2JProtocolException, S2JConfigurationException, S2JProviderException {
		List<WsJmsMessage> messages1;
		try {
			messages1 = this.jms2SoapSerializer.messagesToWs(messages);
		} catch (final JMSException e1) {
			throw new S2JProtocolException(StatusCodeEnum.ERR_SERIALIZATION, "Error serializing messages", e1);
		}
		ResponseStatus<S2JMessage> rspStatus = null;
		try {
			final List<MessageIdAndStatus> result = this.senderSoapPort.sendMessages(queueName, messages1);
			rspStatus = convertStatus(messages, result);
		} catch (final WebServiceException se) {
			if (se.getCause() instanceof IOException) {
				rspStatus = fillStatus(messages, MessageDeliveryStatus.IN_DOUBT, StatusCodeEnum.ERR_NETWORK,
						se.getCause().getMessage());
			} else {
				rspStatus = fillStatus(messages, MessageDeliveryStatus.IN_DOUBT, StatusCodeEnum.ERR_GENERIC,
						se.getCause().getMessage());
			}
			LOGGER.warn("Received exception invoking ws sendMessages.", se);
		} catch (final WsJmsException e) {
			handleWsException(e);
		}
		return rspStatus;
	}

	// TODO refactor. It is the same as convertStatus. Inheritancnce in wsdl can
	// help?
	private ResponseStatus<String> convertAckResponse(final List<String> messages,
			final List<MessageIdAndStatus> result) {
		final ResponseStatus<String> rspStatus = new ResponseStatus<>();
		for (int i = 0; i < messages.size(); i++) {
			final String message = messages.get(i);
			final MessageIdAndStatus status = result.get(i);
			final StatusCode statusCodeAndReason = status.getStatus();
			if (statusCodeAndReason != null) {
				final StatusCodeEnum statusCodeEnum = StatusCodeEnum.valueOf(statusCodeAndReason.getCode());
				final MessageDeliveryStatus deliveryStatus = statusCodeEnum.isError()
						? MessageDeliveryStatus.NOT_DELIVERED : MessageDeliveryStatus.DELIVERED;
				rspStatus.addMessage(message, deliveryStatus, statusCodeEnum, statusCodeAndReason.getReason());
			} else {
				LOGGER.warn("Remote server returned statusCode null " + message + ", messageIdAndStatus:" + status);
				rspStatus.addMessage(message, MessageDeliveryStatus.IN_DOUBT, StatusCodeEnum.ERR_SERIALIZATION,
						"server protocol error");
			}
		}
		return rspStatus;
	}

	// TODO refactor. It is the same as ackresponse
	private ResponseStatus<S2JMessage> convertStatus(final S2JMessage[] messages,
			final List<MessageIdAndStatus> result) {
		final ResponseStatus<S2JMessage> rspStatus = new ResponseStatus<>();
		for (int i = 0; i < messages.length; i++) {
			final S2JMessage message = messages[i];
			final MessageIdAndStatus status = result.get(i);
			final StatusCode statusCodeAndReason = status.getStatus();
			if (statusCodeAndReason != null) {
				final StatusCodeEnum statusCodeEnum = StatusCodeEnum.valueOf(statusCodeAndReason.getCode());
				final MessageDeliveryStatus deliveryStatus = statusCodeEnum.isError()
						? MessageDeliveryStatus.NOT_DELIVERED : MessageDeliveryStatus.DELIVERED;
				rspStatus.addMessage(message, deliveryStatus, statusCodeEnum, statusCodeAndReason.getReason());
			} else {
				LOGGER.warn("Remote server returned statusCode null " + message + ", messageIdAndStatus:" + status);
				rspStatus.addMessage(message, MessageDeliveryStatus.IN_DOUBT, StatusCodeEnum.ERR_INCOMPATIBLE_PROTOCOL,
						"server protocol error");
			}
		}
		return rspStatus;
	}

	private ResponseStatus<S2JMessage> fillStatus(final S2JMessage[] messages,
			final MessageDeliveryStatus deliveryStatus, final StatusCodeEnum errNetwork, final String messageStr) {
		final ResponseStatus<S2JMessage> rspStatus = new ResponseStatus<>();
		for (final S2JMessage message : messages) {
			rspStatus.addMessage(message, deliveryStatus, errNetwork, messageStr);
		}
		return rspStatus;
	}

	/**
	 * This method decodes a wsdl exception into a program exception. it always
	 * throws exception.
	 *
	 * @param e
	 * @throws S2JConfigurationException
	 * @throws S2JProtocolException
	 * @throws S2JProviderException
	 */
	private void handleWsException(final WsJmsException e)
			throws S2JException, S2JConfigurationException, S2JProtocolException, S2JProviderException {
		final WsJmsExceptionData info = e.getFaultInfo();
		if (e.getFaultInfo() != null) {
			final String statusCode = info.getCode();
			StatusCodeEnum sc;
			try {
				sc = StatusCodeEnum.valueOf(statusCode);
			} catch (final RuntimeException e1) {
				LOGGER.warn("Error in protocol, StatusCode not recognized " + statusCode + " " + e1);
				sc = StatusCodeEnum.ERR_GENERIC;
			}
			switch (sc) {
			case ERR_MALFORMED_URL:
			case ERR_QUEUE_NOT_FOUND:
				throw new S2JConfigurationException(sc, info.getReason(), e);
			case ERR_SERIALIZATION:
			case ERR_INCOMPATIBLE_PROTOCOL:
				throw new S2JProtocolException(sc, info.getReason() + " " + info.getOriginalException(), e);
			case ERR_JMS:
				throw new S2JProviderException(sc, info.getReason() + " " + info.getOriginalException(), info.getJmsCode(), e);
			case ERR_GENERIC:
				throw new S2JException(sc, info.getReason() + " " + info.getOriginalException(), e);
			default:
				throw new S2JException(sc, info.getReason() + " " + info.getOriginalException(), e);
			}
		} else {
			throw new S2JProtocolException(StatusCodeEnum.ERR_INCOMPATIBLE_PROTOCOL, "Exception information missing.",
					e);
		}
	}

	/**
	 * Set the connection timeout in different situations.
	 * 
	 * @see https://java.net/jira/browse/JAX_WS-1166
	 * @param spAsBindingProvider
	 */
	private void setTimeouts(final BindingProvider spAsBindingProvider) {
		final Map<String, Object> requestContext = spAsBindingProvider.getRequestContext();
		if (this.configuration.getConnectionTimeout() != null) {
			// wildfly
			requestContext.put("javax.xml.ws.client.connectionTimeout", this.configuration.getConnectionTimeout());
			requestContext.put("com.sun.xml.ws.connect.timeout", this.configuration.getRequestTimeout());
			// must be Integer
			requestContext.put("com.sun.xml.internal.ws.connect.timeout", this.configuration.getRequestTimeout());
		}
		if (this.configuration.getRequestTimeout() != null) {
			// wildfly
			requestContext.put("javax.xml.ws.client.receiveTimeout", this.configuration.getRequestTimeout());
			requestContext.put("com.sun.xml.ws.request.timeout", this.configuration.getRequestTimeout());
			// must be Integer
			requestContext.put("com.sun.xml.internal.ws.request.timeout", this.configuration.getRequestTimeout());
		}

	}
}

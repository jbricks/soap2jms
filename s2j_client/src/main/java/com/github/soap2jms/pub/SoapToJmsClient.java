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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.WsExceptionClass;
import com.github.soap2jms.common.serialization.JMSImplementation;
import com.github.soap2jms.common.serialization.JmsToSoapSerializer;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.StatusCode;
import com.github.soap2jms.common.ws.WsJmsExceptionData;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.model.ClientMessageFactory;
import com.github.soap2jms.model.MessageDeliveryStatus;
import com.github.soap2jms.model.NetworkException;
import com.github.soap2jms.model.ResponseStatus;
import com.github.soap2jms.model.S2JConfigurationException;
import com.github.soap2jms.model.S2JMessage;
import com.github.soap2jms.model.S2JServerException;
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
	private final ThreadLocal<Boolean> isComplete = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.TRUE;
		}
	};
	private static final Logger LOGGER = LoggerFactory.getLogger(SoapToJmsClient.class);
	private final SoapToJmsConfiguration configuration;
	private final ReaderSoap2Jms readerSoapPort;
	private final SenderSoap2Jms senderSoapPort;
	private final SoapToJmsSerializer soap2JmsSerializer;
	private final ClientSerializationUtils jms2SoapSerializer;

	public SoapToJmsClient(final SoapToJmsReaderService readerService, final SoapToJmsSenderService senderService,
			SoapToJmsSerializer s2jser, JmsToSoapSerializer jms2SoapSerializer) {

		this.readerSoapPort = readerService == null ? null : readerService.getReaderSOAP();
		this.senderSoapPort = senderService == null ? null : senderService.getSenderSOAP();
		// FIXME
		this.configuration = new SoapToJmsConfiguration(readerService.getWSDLDocumentLocation().toString());
		this.soap2JmsSerializer = s2jser;
		this.jms2SoapSerializer = new ClientSerializationUtils();
	}

	public SoapToJmsClient(final String contextBase) throws S2JConfigurationException {
		this(new SoapToJmsConfiguration(contextBase));
	}

	public SoapToJmsClient(SoapToJmsConfiguration config) throws S2JConfigurationException {
		this(config, true, true);
	}

	public SoapToJmsClient(final SoapToJmsConfiguration config, final boolean initReader, final boolean initSender)
			throws S2JConfigurationException {
		this.configuration = config;
		String contextBase = config.getContextBase();
		final String contextBaseNorm = contextBase.endsWith("/") ? contextBase : contextBase + "/";
		if (initReader) {
			final String readerUrl = contextBaseNorm + "soapToJmsReaderService?wsdl";
			try {
				final SoapToJmsReaderService soap2JmsService = new SoapToJmsReaderService(readerUrl);
				this.readerSoapPort = soap2JmsService.getReaderSOAP();

				setTimeouts((BindingProvider) this.readerSoapPort);
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
				BindingProvider spAsBindingProvider = (BindingProvider) this.senderSoapPort;
				setTimeouts(spAsBindingProvider);
			} catch (final MalformedURLException e) {
				throw new S2JConfigurationException("Url " + senderUrl + " is malformed.", e);
			}
		} else {
			this.senderSoapPort = null;
		}
		this.soap2JmsSerializer = new SoapToJmsSerializer();
		this.jms2SoapSerializer = new ClientSerializationUtils();
	}

	/**
	 * @see https://java.net/jira/browse/JAX_WS-1166
	 * @param spAsBindingProvider
	 */
	public void setTimeouts(BindingProvider spAsBindingProvider) {
		final Map<String, Object> requestContext = spAsBindingProvider.getRequestContext();
		if (configuration.getConnectionTimeout() != null) {
			// wildfly
			requestContext.put("javax.xml.ws.client.connectionTimeout", configuration.getConnectionTimeout());
			requestContext.put("com.sun.xml.ws.connect.timeout", configuration.getRequestTimeout());
			// must be Integer
			requestContext.put("com.sun.xml.internal.ws.connect.timeout", configuration.getRequestTimeout());
		}
		if (configuration.getRequestTimeout() != null) {
			// wildfly
			requestContext.put("javax.xml.ws.client.receiveTimeout", configuration.getRequestTimeout());
			requestContext.put("com.sun.xml.ws.request.timeout", configuration.getRequestTimeout());
			// must be Integer
			requestContext.put("com.sun.xml.internal.ws.request.timeout", configuration.getRequestTimeout());
		}

	}

	public ResponseStatus acknolwedge(final String queueName, final List<String> messageIds) throws S2JServerException {
		List<MessageIdAndStatus> result;
		try {
			result = this.readerSoapPort.acknowledgeMessages(queueName, messageIds);
		} catch (final WsJmsException e) {
			throw new S2JServerException("", e);
		}
		return null;
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
			throws S2JProtocolException, S2JConfigurationException  {

		RetrieveMessageResponseType wsResponse;
		try {
			wsResponse = this.readerSoapPort.retrieveMessages(queueName, filter, msgMax);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new S2JConfigurationException("", e);
		}
		this.isComplete.set(wsResponse.isComplete());
		return this.soap2JmsSerializer.convertMessages(new ClientMessageFactory(), wsResponse.getS2JMessageAndStatus(),
				JMSImplementation.NONE);
	}

	public boolean retrieveComplete() {
		return this.isComplete.get();
	}

	/**
	 * <p>
	 * This method allows to send messages to a remote queue.
	 * </p>
	 * 
	 * <p>
	 * Exception semantics. If exception is thrown means that:
	 * <ul>
	 * <li>The whole call failed. All the messages were not delivered.</li>
	 * <li>The service was called in a way that is not correct. Subsequent calls
	 * will result in an exception.</li>
	 * <li>Developer's attention is required: libraries upgrade or p</li>
	 * </ul>
	 * </p>
	 * 
	 * @param queueName
	 *            name of the queue, may be a symbolic name. If null the
	 *            messages will be sent to the default queue on server.
	 * @param messages
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
	 */
	public ResponseStatus sendMessages(final String queueName, final S2JMessage[] messages)
			throws S2JProtocolException, S2JConfigurationException {
		List<WsJmsMessage> messages1;
		try {
			messages1 = jms2SoapSerializer.messagesToWs(messages);
		} catch (final JMSException e1) {
			throw new S2JProtocolException(StatusCodeEnum.ERR_SERIALIZATION, "", e1);
		}
		ResponseStatus rspStatus = null;
		try {
			List<MessageIdAndStatus> result = this.senderSoapPort.sendMessages(null, queueName, messages1);
			rspStatus = convertStatus(messages, result);
			// } catch (ProtocolException se){
		} catch (WebServiceException se) {
			if (se.getCause() instanceof IOException) {
				rspStatus = fillStatus(messages, MessageDeliveryStatus.UNKNOWN, StatusCodeEnum.ERR_NETWORK,
						se.getCause().getMessage());
			} else {
				rspStatus = fillStatus(messages, MessageDeliveryStatus.UNKNOWN, StatusCodeEnum.ERR_GENERIC,
						se.getCause().getMessage());
			}
			LOGGER.warn("Received exception invoking ws.", se);
		} catch (final WsJmsException e) {
			WsJmsExceptionData info = e.getFaultInfo();
			String excClass = info.getExceptionClass();
			WsExceptionClass excClassEnum;
			try{
			excClassEnum = WsExceptionClass.valueOf(excClass);
			} catch(RuntimeException e1){
				LOGGER.warn("Error in protocol, exception class not found " + excClass + " " + e1);
				excClassEnum = WsExceptionClass.OTHER;
			}
			//status code from string
			Integer errorCode = info.getCode();
			switch(excClassEnum){
			case CONFIGURATION:
				throw new S2JConfigurationException("",e);
			case PROTOCOL:
			case OTHER:
				throw new S2JProtocolException(StatusCodeEnum.ERR_GENERIC);
			}

		}
		return rspStatus;
	}

	private ResponseStatus fillStatus(S2JMessage[] messages, MessageDeliveryStatus deliveryStatus,
			StatusCodeEnum errNetwork, String messageStr) {
		ResponseStatus rspStatus = new ResponseStatus();
		for (int i = 0; i < messages.length; i++) {
			S2JMessage message = messages[i];
			rspStatus.addMessage(message, deliveryStatus, errNetwork, messageStr);
		}
		return rspStatus;
	}

	private ResponseStatus convertStatus(S2JMessage[] messages, List<MessageIdAndStatus> result) {
		ResponseStatus rspStatus = new ResponseStatus();
		for (int i = 0; i < messages.length; i++) {
			S2JMessage message = messages[i];
			MessageIdAndStatus status = result.get(i);
			final StatusCode statusCodeAndReason = status.getStatus();
			if (statusCodeAndReason != null) {
				StatusCodeEnum statusCodeEnum = StatusCodeEnum.valueOf(statusCodeAndReason.getCode());
				MessageDeliveryStatus deliveryStatus = statusCodeEnum.isError() ? MessageDeliveryStatus.NOT_DELIVERED
						: MessageDeliveryStatus.DELIVERED;
				rspStatus.addMessage(message, deliveryStatus, statusCodeEnum, statusCodeAndReason.getReason());
			} else {
				LOGGER.warn("Remote server returned statusCode null " + message + ", messageIdAndStatus:" + status);
				rspStatus.addMessage(message, MessageDeliveryStatus.UNKNOWN, StatusCodeEnum.ERR_SERIALIZATION,
						"server protocol error");
			}
		}
		return rspStatus;
	}
}

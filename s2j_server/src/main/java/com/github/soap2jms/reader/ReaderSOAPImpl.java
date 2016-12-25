
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.github.soap2jms.reader;

import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.WsExceptionClass;
import com.github.soap2jms.common.serialization.JMSImplementation;
import com.github.soap2jms.common.serialization.JmsToSoapSerializer;
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.queue.GetMessagesResult;
import com.github.soap2jms.queue.QueueInspector;
import com.github.soap2jms.service.ReaderSoap2Jms;
import com.github.soap2jms.service.WsJmsException;

@javax.jws.WebService(serviceName = "soapToJmsReaderService", portName = "readerSOAP", targetNamespace = "http://soap2jms.github.com/service")
public class ReaderSOAPImpl implements ReaderSoap2Jms {

	private static final Logger LOG = LoggerFactory.getLogger(ReaderSOAPImpl.class);
	@Inject
	private QueueInspector qi;
	@Inject
	private JmsToSoapSerializer serializationUtils;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.github.soap2jms.reader.ReaderSoap2JmsGithubCom#acknowledgeMessages(
	 * java.util.List<java.lang.String> messageId)*
	 */
	@Override
	public java.util.List<MessageIdAndStatus> acknowledgeMessages(final String queueName, final List<String> messageIds)
			throws WsJmsException {
		LOG.info("Executing operation acknowledgeMessages");
		try {
			return this.qi.acknolwedge(queueName, messageIds);
		} catch (final java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public RetrieveMessageResponseType retrieveMessages(final String queueName, final String filter, final int maxItems)
			throws WsJmsException {

		final RetrieveMessageResponseType result = new RetrieveMessageResponseType();
		try {
			final GetMessagesResult messages = this.qi.getMessages(queueName, maxItems, filter);

			result.setComplete(!messages.moreMessages);

			for (final Message msg : messages.result) {
				result.getS2JMessageAndStatus().add(
						this.serializationUtils.jmsToSoapMessageAndStatus(msg, JMSImplementation.ARTEMIS_ACTIVE_MQ));
			}
		} catch (final JMSException e) {
			LOG.error("JMS error processing [" + queueName + "] filter[" + filter + "]", e);
			throw new WsJmsException("Internal server processing [" + queueName + "] filter[" + filter + "]",
					e.toString(), StatusCodeEnum.ERR_GENERIC, WsExceptionClass.OTHER);
		} catch (final Exception ex) {
			LOG.error("Generic error processing [" + queueName + "] filter[" + filter + "]", ex);
			throw new WsJmsException("Internal server processing [" + queueName + "] filter[" + filter + "]",
					ex.toString(), StatusCodeEnum.ERR_GENERIC, WsExceptionClass.OTHER);
		}
		return result;
	}

}

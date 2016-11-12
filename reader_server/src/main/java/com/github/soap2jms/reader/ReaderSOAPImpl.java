
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.github.soap2jms.reader;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.queue.GetMessagesResult;
import com.github.soap2jms.queue.QueueInspector;
import com.github.soap2jms.reader.common.WsExceptionClass;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.utils.ServerSerializationUtils;

@javax.jws.WebService(serviceName = "jmsReaderSoap", 
	portName = "readerSOAP", targetNamespace = "http://soap2jms.github.com/reader", 
	endpointInterface = "com.github.soap2jms.reader.ReaderSoap2Jms")
public class ReaderSOAPImpl implements ReaderSoap2Jms {

	private static final Logger LOG = LoggerFactory.getLogger(ReaderSOAPImpl.class.getName());
	@Inject
	private QueueInspector qi;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.github.soap2jms.reader.ReaderSoap2JmsGithubCom#acknowledgeMessages(
	 * java.util.List<java.lang.String> messageId)*
	 */
	@Override
	public java.util.List<com.github.soap2jms.reader.common.ws.MessageIdAndStatus> acknowledgeMessages(
			final java.util.List<java.lang.String> messageId) throws WsJmsException {
		LOG.info("Executing operation acknowledgeMessages");
		System.out.println(messageId);
		try {
			final java.util.List<com.github.soap2jms.reader.common.ws.MessageIdAndStatus> _return = new java.util.ArrayList<>();
			final com.github.soap2jms.reader.common.ws.MessageIdAndStatus _returnVal1 = new com.github.soap2jms.reader.common.ws.MessageIdAndStatus();
			final java.util.List<java.lang.Object> _returnVal1MessageIdAndStatus = new java.util.ArrayList<>();
			final java.lang.Object _returnVal1MessageIdAndStatusVal1 = null;
			_returnVal1MessageIdAndStatus.add(_returnVal1MessageIdAndStatusVal1);
			// _returnVal1.getMessageIdAndStatus().addAll(_returnVal1MessageIdAndStatus);
			_return.add(_returnVal1);
			return _return;
		} catch (final java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public RetrieveMessageResponseType retrieveMessages(final String queueName, final String filter, final int maxItems)
			throws WsJmsException {

		try {
			final GetMessagesResult messages = this.qi.getMessages(queueName, maxItems, filter);

			final com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType result = new RetrieveMessageResponseType();
			result.setComplete(!messages.moreMessages);

			for (final Message msg : messages.result) {
				result.getS2JMessageAndStatus().add(ServerSerializationUtils.jms2soap(msg));
			}
			return result;
		} catch (JMSException e){
			LOG.error("JMS error processing [" +queueName+"] filter["+filter+"]",e);
			throw new WsJmsException("Internal server processing [" +queueName+"] filter["+filter+"]", 
					e.toString(),0,WsExceptionClass.JMS);
		} catch (Exception ex) {
			//FIXME:error type
			LOG.error("JMS error processing [" +queueName+"] filter["+filter+"]",ex);
			throw new WsJmsException("Internal server processing [" +queueName+"] filter["+filter+"]", 
					ex.toString(),0,WsExceptionClass.OTHER);
		}
	}

}

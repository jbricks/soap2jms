La spackage com.github.soap2jms.pub;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import com.github.soap2jms.common.S2JProtocolException;
import com.github.soap2jms.common.serialization.JmsToSoapSerializer;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.common.ws.WsJmsMessage;
import com.github.soap2jms.model.ClientMessageFactory;
import com.github.soap2jms.model.InternalServerException;
import com.github.soap2jms.model.NetworkException;
import com.github.soap2jms.model.S2JMessage;
import com.github.soap2jms.service.ReaderSoap2Jms;
import com.github.soap2jms.service.SenderSoap2Jms;
import com.github.soap2jms.service.SoapToJmsService;
import com.github.soap2jms.service.WsJmsException;

public class SoapToJmsClient {
	private final SenderSoap2Jms senderSoap2Jms;
	private final ReaderSoap2Jms rs;
	private final SoapToJmsSerializer soap2JmsSerializer = new SoapToJmsSerializer();
	private final ThreadLocal<Boolean> isComplete = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.TRUE;
		}
	};

	public SoapToJmsClient(final SoapToJmsService readerSoap) {
		this.rs = readerSoap.getReaderSOAP();
		senderSoap2Jms = readerSoap.getSenderSOAP();
	}

	public SoapToJmsClient(final String wsdlLocation) {
		SoapToJmsService soap2JmsService;
		try {
			soap2JmsService = new SoapToJmsService(wsdlLocation);
		} catch (final MalformedURLException e) {
			throw new IllegalArgumentException("Url " + wsdlLocation + " is malformed.", e);
		}
		senderSoap2Jms = soap2JmsService.getSenderSOAP();
		rs = soap2JmsService.getReaderSOAP();
	}

	public void acknolwedge(String queueName, final List<String> messageIds) throws InternalServerException {
		try {
			rs.acknowledgeMessages(queueName, messageIds);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
	}

	public void acknowledge(String queueName, final S2JMessage[] messages)
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
			wsResponse = rs.retrieveMessages(queueName, filter, msgMax);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
		this.isComplete.set(wsResponse.isComplete());
		return soap2JmsSerializer.convertMessages(new ClientMessageFactory(),wsResponse.getS2JMessageAndStatus());
	}

	public void sendMessages(final String queueName, final S2JMessage[] messages)
			throws S2JProtocolException, NetworkException, InternalServerException {

		List<WsJmsMessage> messages1 = new JmsToSoapSerializer().messagesToWs(messages);
		try {
			senderSoap2Jms.sendMessages(queueName, null, messages1);

		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
	}
}

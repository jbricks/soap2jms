package com.github.soap2jms.reader.pub;

import java.net.MalformedURLException;
import java.util.Collection;

import javax.jms.Message;

import com.github.soap2jms.reader.JmsReaderSoap;
import com.github.soap2jms.reader.ReaderSoap2Jms;
import com.github.soap2jms.reader.WsJmsException;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.model.ClientSerializationUtils;
import com.github.soap2jms.reader.model.InternalServerException;
import com.github.soap2jms.reader.model.NetworkException;
import com.github.soap2jms.reader.model.S2JMessage;
import com.github.soap2jms.reader.model.S2JProtocolException;

public class S2JReaderClient {
	private final JmsReaderSoap readerSoap;

	public S2JReaderClient(final String wsdlLocation) {
		try {
			this.readerSoap = new JmsReaderSoap(wsdlLocation);
		} catch (final MalformedURLException e) {
			throw new IllegalArgumentException("Url " + wsdlLocation + " is malformed.", e);
		}
	}

	public S2JReaderClient(JmsReaderSoap readerSoap) {
		this.readerSoap = readerSoap;
	}

	void acknolwedge(final Collection<String> messageId) {

	}

	void acknowledge(final Message[] messages) {

	}

	S2JMessage[] readMessages(final String queueName, final String filter, final int msgMax)
			throws S2JProtocolException, NetworkException, InternalServerException {
		final ReaderSoap2Jms rs = this.readerSoap.getReaderSOAP();
		RetrieveMessageResponseType msgType;
		try {
			msgType = rs.retrieveMessages(queueName, filter, msgMax);
		} catch (final WsJmsException e) {
			// FIXME: error handling.
			throw new InternalServerException("", e);
		}
		return ClientSerializationUtils.convertMessages(msgType);
	}
}

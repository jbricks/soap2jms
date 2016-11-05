package com.github.soap2jms.reader.pub;

import java.net.MalformedURLException;
import java.util.Collection;

import javax.jms.Message;

import com.github.soap2jms.reader.JmsReaderSoap;
import com.github.soap2jms.reader.ReaderSoap2Jms;
import com.github.soap2jms.reader.S2JmsException;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.model.ClientSerializationUtils;
import com.github.soap2jms.reader.model.InternalServerException;
import com.github.soap2jms.reader.model.NetworkException;
import com.github.soap2jms.reader.model.S2JProtocolException;
import com.github.soap2jms.reader.model.S2JMessage;

public class S2JReaderClient {
	private final JmsReaderSoap readerSoap;

	public S2JReaderClient(String wsdlLocation) {
		try {
			readerSoap = new JmsReaderSoap(wsdlLocation);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Url " + wsdlLocation + " is malformed.", e);
		}
	}

	S2JMessage[] readMessages(String queueName, String filter, int msgMax)
			throws S2JProtocolException, NetworkException, InternalServerException {
		ReaderSoap2Jms rs = readerSoap.getReaderSOAP();
		RetrieveMessageResponseType msgType;
		try {
			msgType = rs.retrieveMessages(queueName, filter, msgMax);
		} catch (S2JmsException e) {
			//FIXME: error handling.
			throw new InternalServerException("", e);
		}
		return ClientSerializationUtils.convertMessages(msgType);
	}

	void acknowledge(Message[] messages) {

	}

	void acknolwedge(Collection<String> messageId) {

	}
}

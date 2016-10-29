package com.github.soap2jms.reader.pub;

import java.net.MalformedURLException;
import java.util.Collection;

import javax.jms.Message;

import com.github.soap2jms.reader.JmsReaderSoap;

public class S2JReaderClient {
	private final JmsReaderSoap readerSoap;

	public S2JReaderClient(String wsdlLocation) {
		try {
			readerSoap = new JmsReaderSoap(wsdlLocation);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Url " + wsdlLocation + " is malformed.", e);
		}
	}

	Message[] readMessages(String queueName, String filter, int msgMax) {
		return null;
	}

	void acknowledge(Message[] messages) {

	}

	void acknolwedge(Collection<String> messageId) {

	}
}

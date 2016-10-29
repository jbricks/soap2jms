package com.github.soap2jms.reader.model;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.io.IOUtils;

public class S2JTextMessage extends S2JMessage implements TextMessage {

	public S2JTextMessage(com.github.soap2jms.reader.common.ws.S2JMessage message) {
		super(message);
	}

	@Override
	public void setText(String string) throws JMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getText() throws JMSException {
		String text = null;
		final DataHandler messageBody = message.getBody();
		try {
			if(messageBody !=null && messageBody.getInputStream() != null){
				text = IOUtils.toString(messageBody.getInputStream(),"UTF-8");
			}
		} catch (IOException e) {
			JMSException e1 = new JMSException("Client error deserializing body", "1");
			e1.initCause(e);
			e1.setLinkedException(e);
			throw e1;
		}
		return text;
	}

}

package com.github.soap2jms.queue;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

public class QueueInspector {
	private static final String JNDI_LOCAL = "java:comp/env/";

	@Inject
	private JMSContext ctx;

	public GetMessagesResult getMessages(String queueName, int msgMax, String filter)
			throws JMSException, NamingException {
		List<Message> result = new ArrayList<>();
		boolean moreMessages;
		InitialContext ictx = null;
		QueueBrowser browser = null;
		final GetMessagesResult messageResult;
		try {
			ictx = new InitialContext();
			Queue queue = (Queue) ictx.lookup(JNDI_LOCAL + queueName);
			if (StringUtils.isNotBlank(filter)) {
				browser = ctx.createBrowser(queue, filter);
			} else {
				browser = ctx.createBrowser(queue);
			}
			@SuppressWarnings("unchecked")
			Enumeration<Message> msgs = browser.getEnumeration();
			int i = 0;
			while (msgs.hasMoreElements() && i < msgMax) {
				Message tempMsg = msgs.nextElement();
				result.add(tempMsg);
				i++;
			}
			moreMessages = msgs.hasMoreElements();
			messageResult = new GetMessagesResult(
					(Message[]) result.toArray(new Message[result.size()]), moreMessages);
		} finally {
			browser.close();
			ictx.close();
		}
		return messageResult;

	}
}

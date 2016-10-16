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

	@Inject
	private JMSContext ctx;

	public static class GetMessagesResult {
		public GetMessagesResult(Message[] result, boolean moreMessages) {
			this.result = result;
			this.moreMessages = moreMessages;
		}

		public final Message[] result;
		public final boolean moreMessages;
	}

	public GetMessagesResult getMessages(String queueName, int msgMax, String filter)
			throws JMSException, NamingException {
		List<Message> result = new ArrayList<>();
		boolean moreMessages;
		InitialContext ictx = new InitialContext();
		Queue queue = (Queue) ictx.lookup("java:/comp/env/" + queueName);
		QueueBrowser browser = null;
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

		return new GetMessagesResult((Message[]) result.toArray(new Message[result.size()]), moreMessages);

	}
}

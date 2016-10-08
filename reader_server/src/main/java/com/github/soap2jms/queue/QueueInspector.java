package com.github.soap2jms.queue;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.Session;

public class QueueInspector {

	@Resource
	private ConnectionFactory cf;

	public static class GetMessagesResult{
		public GetMessagesResult(Message[] result, boolean moreMessages) {
			this.result = result;
			this.moreMessages = moreMessages;
		}
		public final Message[] result;
		public final boolean moreMessages;
	}
	
	public GetMessagesResult getMessages(int msgMax, String queueName, String filter) throws JMSException {
		List<Message> result = new ArrayList<>();
		Connection connection = cf.createConnection();
		boolean moreMessages;
		try {
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueBrowser browser=null;
			/*if (StringUtils.isNotBlank(filter)) {
				browser = session.createBrowser(queue, filter);
			} else {
				browser = session.createBrowser(queue);
			}*/
			Enumeration<Message> msgs = browser.getEnumeration();
			int i=0;
			while (msgs.hasMoreElements() && i<msgMax) {
				Message tempMsg = msgs.nextElement();
				result.add(tempMsg);
				i++;
			}
			moreMessages = msgs.hasMoreElements();
		} finally {
			connection.close();
		}
		return new GetMessagesResult((Message[]) result.toArray(new Message[result.size()]),moreMessages);

	}
}

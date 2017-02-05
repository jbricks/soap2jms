package com.github.soap2jms.queue;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.inject.Inject;
import javax.jms.BytesMessage;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.serialization.JMSMessageFactory;
import com.github.soap2jms.common.ws.MessageIdAndStatus;

public class QueueInspector {
	private static final String JNDI_LOCAL = "java:comp/env/";
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueInspector.class);
	@Inject
	private JMSContext ctx;

	public List<MessageIdAndStatus> acknolwedge(final String queueName, final List<String> messageIds)
			throws JMSException, NamingException {
		InitialContext ictx = null;
		final List<MessageIdAndStatus> messageResult = new ArrayList<>();
		try {
			ictx = new InitialContext();
			final Queue queue = (Queue) ictx.lookup(JNDI_LOCAL + queueName);
			for (final String messageId : messageIds) {
				final String selector = "JMSMessageID='" + messageId + "'";
				final JMSConsumer browser = this.ctx.createConsumer(queue, selector, true);
				final Message msg = browser.receiveNoWait();
				if (msg != null) {
					// msg.acknowledge();
					messageResult.add(new MessageIdAndStatus(messageId));
				} else {
					messageResult.add(new MessageIdAndStatus(messageId, StatusCodeEnum.WARN_MSG_NOT_FOUND, "", null));
				}
				browser.close();
			}
		} finally {
			ictx.close();
		}
		return messageResult;
	}

	public JMSMessageFactory getJmsMessageFactory() {
		final JMSContext localCopy = this.ctx;
		return new JMSMessageFactory() {
			@Override
			public BytesMessage createBytesMessage() {
				return localCopy.createBytesMessage();
			}

			@Override
			public MapMessage createMapMessage() {
				return localCopy.createMapMessage();
			}

			@Override
			public ObjectMessage createObjectMessage(final Serializable object) {
				return localCopy.createObjectMessage(object);
			}

			@Override
			public StreamMessage createStreamMessage(final InputStream is) throws IOException, JMSException {
				final StreamMessage sm = localCopy.createStreamMessage();
				final byte[] buffer = new byte[1024 * 1024];
				int n;
				while ((n = is.read(buffer)) > 0) {
					sm.writeBytes(buffer, 0, n);
				}
				return sm;
			}

			@Override
			public TextMessage createTextMessage(final String text) {
				return localCopy.createTextMessage(text);
			}

		};
	}

	public GetMessagesResult getMessages(final String queueName, final int msgMax, final String filter)
			throws JMSException, NamingException {
		final List<Message> result = new ArrayList<>();
		boolean moreMessages;
		InitialContext ictx = null;
		QueueBrowser browser = null;
		final GetMessagesResult messageResult;
		try {
			ictx = new InitialContext();
			final Queue queue = (Queue) ictx.lookup(JNDI_LOCAL + queueName);
			if (StringUtils.isNotBlank(filter)) {
				browser = this.ctx.createBrowser(queue, filter);
			} else {
				browser = this.ctx.createBrowser(queue);
			}
			@SuppressWarnings("unchecked")
			final Enumeration<Message> msgs = browser.getEnumeration();
			int i = 0;
			while (msgs.hasMoreElements() && i < msgMax) {
				final Message tempMsg = msgs.nextElement();
				result.add(tempMsg);
				i++;
			}
			moreMessages = msgs.hasMoreElements();
			messageResult = new GetMessagesResult(result.toArray(new Message[result.size()]), moreMessages);
		} finally {
			browser.close();
			ictx.close();
		}
		return messageResult;
	}

}

package com.github.soap2jms.queue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.inject.Inject;
import javax.jms.BytesMessage;
import javax.jms.InvalidDestinationRuntimeException;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageFormatRuntimeException;
import javax.jms.MessageNotWriteableRuntimeException;
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
import com.github.soap2jms.common.ws.MessageIdAndStatus;
import com.github.soap2jms.reader.utils.JMSMessageFactory;

public class QueueInspector {
	private static final String JNDI_LOCAL = "java:comp/env/";
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueInspector.class);
	@Inject
	private JMSContext ctx;

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

	public List<MessageIdAndStatus> acknolwedge(final String queueName, final List<String> messageIds)
			throws JMSException, NamingException {
		InitialContext ictx = null;
		final List<MessageIdAndStatus> messageResult = new ArrayList<>();
		try {
			ictx = new InitialContext();
			final Queue queue = (Queue) ictx.lookup(JNDI_LOCAL + queueName);
			for (String messageId : messageIds) {
				String selector = "JMSMessageID='" + messageId + "'";
				JMSConsumer browser = this.ctx.createConsumer(queue, selector, true);
				Message msg = browser.receiveNoWait();
				if (msg != null) {
					// msg.acknowledge();
					messageResult.add(new MessageIdAndStatus(messageId));
				} else {
					messageResult.add(new MessageIdAndStatus(messageId, StatusCodeEnum.WARN_MSG_NOT_FOUND, ""));
				}
				browser.close();
			}
		} finally {
			ictx.close();
		}
		return messageResult;
	}

	public IdAndStatus[] sendMessages(final String queueName, final Message[] messages)
			throws JMSException, NamingException {
		final IdAndStatus[] result = new IdAndStatus[messages.length];
		InitialContext ictx = null;
		JMSProducer producer = null;
		try {
			ictx = new InitialContext();
			final String jndiName = JNDI_LOCAL + queueName;
			final Queue queue = (Queue) ictx.lookup(jndiName);

			producer = this.ctx.createProducer();
			for (int i=0;i<messages.length;i++) {
				Message message = messages[i];
				try {
					producer.send(queue, message);
					result[i] = new IdAndStatus(message.getJMSMessageID());
				} catch (MessageFormatRuntimeException e) {
					e.printStackTrace();
				} catch (InvalidDestinationRuntimeException e) {
					String errmessage = "Destination " + queue + " is not valid. Name ["
							+ queueName + "] jndiName [" + jndiName + "]";
					LOGGER.error(errmessage,e);
				} catch(MessageNotWriteableRuntimeException e){
				} catch(JMSRuntimeException e){
				} catch (RuntimeException e){
					
				}
			}
		} finally {
			ictx.close();
		}
		return result;
	}

	public JMSMessageFactory getJmsMessageFactory() {
		final JMSContext localCopy =ctx;
		return new JMSMessageFactory() {
			@Override
			public StreamMessage createStreamMessage(){
				return localCopy.createStreamMessage();
			}

			@Override
			public ObjectMessage createObjectMessage(Serializable object) {
				return createObjectMessage(object);
			}

			@Override
			public ObjectMessage createObjectMessage() {
				return localCopy.createObjectMessage();
			}

			@Override
			public Message createMessage() {
				return localCopy.createMessage();
			}

			@Override
			public MapMessage createMapMessage() {
				return localCopy.createMapMessage();
			}

			@Override
			public BytesMessage createBytesMessage() {
				return localCopy.createBytesMessage();
			}

			@Override
			public TextMessage createTextMessage(String text) {
				return localCopy.createTextMessage(text);
			}
			
			
		};
	}

}

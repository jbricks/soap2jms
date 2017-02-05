package com.github.soap2jms.queue;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.inject.Inject;
import javax.jms.BytesMessage;
import javax.jms.InvalidDestinationRuntimeException;
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
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.common.S2JConfigurationException;
import com.github.soap2jms.common.S2JProviderException;
import com.github.soap2jms.common.StatusCodeEnum;
import com.github.soap2jms.common.serialization.JMSMessageFactory;

public class QueueSender {
	private static final String JNDI_LOCAL = "java:comp/env/";
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueSender.class);

	@Inject
	private JMSContext ctx;
	private InitialContext ictx = null;
	private String jndiName;
	private JMSProducer producer = null;
	private Queue queue = null;

	public QueueSender() {
	}

	public void close() {
		try {
			this.ictx.close();
		} catch (final Exception e) {
			LOGGER.error("error closing context", e);
		}
		this.producer = null;
		this.queue = null;
		this.ictx = null;
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

	public void open(final String queueName) throws S2JConfigurationException, S2JProviderException {
		if (this.ctx == null) {
			throw new S2JProviderException(StatusCodeEnum.ERR_JMS, "Initial JMSContext not found", null, null);
		}
		this.jndiName = JNDI_LOCAL + queueName;
		try {
			this.ictx = new InitialContext();
			this.queue = (Queue) this.ictx.lookup(this.jndiName);
			this.producer = this.ctx.createProducer();
		} catch (final NameNotFoundException e) {
			throw new S2JConfigurationException(StatusCodeEnum.ERR_QUEUE_NOT_FOUND,
					"Error retrieving queue[" + queueName + "]", e);
		} catch (final NamingException e) {
			throw new S2JProviderException(StatusCodeEnum.ERR_JMS,
					"Error retrieving queue[" + queueName + "], jndiName[" + this.jndiName + "]", null, e);
		}
	}

	public IdAndStatus sendMessage(final Message message) {
		IdAndStatus result;

		try {
			this.producer.send(this.queue, message);
			result = new IdAndStatus(message.getJMSMessageID());
		} catch (final MessageFormatRuntimeException e) {
			final String errmessage = "MessageFormat error sending message to " + this.queue + " jndiName ["
					+ this.jndiName + "]" + e;
			LOGGER.error(errmessage, e);
			result = new IdAndStatus(StatusCodeEnum.ERR_JMS, e.getErrorCode(), errmessage);
		} catch (final InvalidDestinationRuntimeException e) {
			final String errmessage = "Destination " + this.queue + " is not valid. Name [" + this.jndiName
					+ "] jndiName [" + this.jndiName + "]" + e;
			LOGGER.error(errmessage, e);
			result = new IdAndStatus(StatusCodeEnum.ERR_QUEUE_NOT_FOUND, e.getErrorCode(), errmessage);
		} catch (final MessageNotWriteableRuntimeException e) {
			final String errmessage = "Message is not writeable. " + this.queue + " jndiName [" + this.jndiName + "]"
					+ e;
			LOGGER.error(errmessage, e);
			result = new IdAndStatus(StatusCodeEnum.ERR_JMS, e.getErrorCode(), errmessage);
		} catch (final JMSRuntimeException e) {
			final String errmessage = "Generic jms error sending message to " + this.queue + " jndiName ["
					+ this.jndiName + "]" + e;
			LOGGER.error(errmessage, e);
			result = new IdAndStatus(StatusCodeEnum.ERR_JMS, e.getErrorCode(), errmessage);
		} catch (final RuntimeException e) {
			final String errmessage = "Generic jms error sending message to " + this.queue + " jndiName ["
					+ this.jndiName + "]" + e;
			LOGGER.error(errmessage, e);
			result = new IdAndStatus(StatusCodeEnum.ERR_JMS, null, errmessage);
		} catch (final JMSException e) {
			final String errmessage = "Generic jms error sending message to " + this.queue + " jndiName ["
					+ this.jndiName + "]" + e;
			LOGGER.error(errmessage, e);
			result = new IdAndStatus(StatusCodeEnum.ERR_JMS, e.getErrorCode(), errmessage);
		}
		return result;
	}

}

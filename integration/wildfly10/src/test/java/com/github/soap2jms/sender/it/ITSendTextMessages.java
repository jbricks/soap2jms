package com.github.soap2jms.sender.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.Constants;
import com.github.soap2jms.common.serialization.SoapToJmsSerializer;
import com.github.soap2jms.model.ResponseStatus;
import com.github.soap2jms.model.S2JMessage;
import com.github.soap2jms.model.S2JTextMessage;
import com.github.soap2jms.pub.SoapToJmsClient;

public class ITSendTextMessages {
	private static final Logger log = LoggerFactory.getLogger(ITSendTextMessages.class);

	// Set up all the default values
	private static final String DEFAULT_CONTENT = "Hello, World!";
	private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String DEFAULT_DESTINATION = "jms/soap2jms.reader";
	private static final String DEFAULT_USERNAME = "test";
	private static final String DEFAULT_PASSWORD = "test1";
	private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
	private static SoapToJmsClient wsClient;

	@BeforeClass
	public static void startWebService() throws Exception {
		wsClient = new SoapToJmsClient(Constants.CTX_NAME);
	}

	@Before
	public void emptyQueue() throws Exception {
		consumeMessages();
	}

	/**
	 * The client send two text messages with a null message id. The server
	 * queues two messages.
	 * 
	 * the server generates two different messageIds for these messages.
	 */
	@Test
	public void sendTextMessageServerGeneratedId() throws Exception {
		// TODO: map jms/soap2jms.reader to java:/comp/env/soap2jms in project
		// project_customization (web.xml).
		S2JTextMessage s2jt = new S2JTextMessage(null, Constants.DEFAULT_CONTENT);
		ResponseStatus responseStatus = wsClient.sendMessages("soap2jms", new S2JMessage[] { s2jt, s2jt });
		assertEquals("ErrorCount", 0, responseStatus.getErrorCount());
		assertEquals("Messages sent", 2, responseStatus.getTotalCount());
		Message[] serverMessages = consumeMessages();
		assertEquals("messages on server", 2, serverMessages.length);
		Message message1 = serverMessages[0];
		Message message2 = serverMessages[1];
		assertTrue("Message instanceof textMessage", message1 instanceof TextMessage);
		TextMessage textMessage = (TextMessage) message1;
		assertEquals("message content", DEFAULT_CONTENT, textMessage.getText());
		assertTrue("Message has server generated Id", StringUtils.isNoneBlank(textMessage.getJMSMessageID()));
		assertFalse("Second message has a different messageId",
				textMessage.getJMSMessageID().equals(message2.getJMSMessageID()));
	}

	/**
	 * Send one message with a JMS id, send a second message with the same JMS
	 * id.
	 * 
	 * The server must deduplicate the message and queue only one.
	 */
	@Test
	public void deduplicationWithClientMessageId() throws Exception {
		S2JTextMessage s2jt = new S2JTextMessage("1", Constants.DEFAULT_CONTENT);
		ResponseStatus responseStatus = wsClient.sendMessages("soap2jms", new S2JMessage[] { s2jt });
		assertEquals("ErrorCount", 0, responseStatus.getErrorCount());
		assertEquals("Messages sent", 1, responseStatus.getTotalCount());
		responseStatus = wsClient.sendMessages("soap2jms", new S2JMessage[] { s2jt });
		assertEquals("ErrorCount", 0, responseStatus.getErrorCount());
		assertEquals("Messages sent", 1, responseStatus.getTotalCount());
		Message[] serverMessages = consumeMessages();
		assertEquals("messages on server", 1, serverMessages.length);
		Message message1 = serverMessages[0];
		assertTrue("Message instanceof textMessage", message1 instanceof TextMessage);
		TextMessage textMessage = (TextMessage) message1;
		assertEquals("message content", DEFAULT_CONTENT, textMessage.getText());
		assertEquals("Message has client Id", "1", textMessage.getStringProperty(SoapToJmsSerializer.ACTIVEMQ_DUPLICATE_ID));
	}

	/**
	 * Send one message with a client generated JMS id, check the message is
	 * queued with the same jms message id.
	 * 
	 * The server must deduplicate the message and queue only one.
	 */
	@Test
	public void clientMessageIdIsPreserved() throws Exception {
		S2JTextMessage s2jt = new S2JTextMessage("3", Constants.DEFAULT_CONTENT);
		ResponseStatus responseStatus = wsClient.sendMessages("soap2jms", new S2JMessage[] { s2jt });
		assertEquals("ErrorCount", 0, responseStatus.getErrorCount());
		assertEquals("Messages sent", 1, responseStatus.getTotalCount());
		Message[] serverMessages = consumeMessages();
		assertEquals("messages on server", 1, serverMessages.length);
		Message message1 = serverMessages[0];
		assertTrue("Message instanceof textMessage", message1 instanceof TextMessage);
		TextMessage textMessage = (TextMessage) message1;
		assertEquals("message content", DEFAULT_CONTENT, textMessage.getText());
		assertEquals("Message has client Id", "3", textMessage.getStringProperty(SoapToJmsSerializer.ACTIVEMQ_DUPLICATE_ID));
	}

	private Message[] consumeMessages() throws NamingException {
		Context namingContext = null;
		List<Message> receivedMsg = new ArrayList<Message>();
		try {
			String userName = System.getProperty("username", DEFAULT_USERNAME);
			String password = System.getProperty("password", DEFAULT_PASSWORD);

			// Set up the namingContext for the JNDI lookup
			final Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
			env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
			// env.put(Context.SECURITY_PRINCIPAL, userName);
			// env.put(Context.SECURITY_CREDENTIALS, password);
			namingContext = new InitialContext(env);

			// Perform the JNDI lookups
			String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
			log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
			ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
			log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

			String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
			log.info("Attempting to acquire destination \"" + destinationString + "\"");
			Destination destination = (Destination) namingContext.lookup(destinationString);
			log.info("Found destination \"" + destinationString + "\" in JNDI");

			try (JMSContext context = connectionFactory.createContext(userName, password)) {
				// Send the specified number of messages
				JMSConsumer consumer = context.createConsumer(destination);
				Message message;
				while ((message = consumer.receiveNoWait()) != null) {
					receivedMsg.add(message);
				}
				consumer.close();
				/*
				 * // Create the JMS consumer JMSConsumer consumer =
				 * context.createConsumer(destination); // Then receive the same
				 * number of messages that were sent for (int i = 0; i < count;
				 * i++) { String text = consumer.receiveBody(String.class,
				 * 5000); log.info("Received message with content " + text); }
				 */
			}
			log.info("received " + receivedMsg.size() + " messages.");
		} finally {
			if (namingContext != null) {
				try {
					namingContext.close();
				} catch (NamingException e) {
					log.error(e.getMessage());
				}
			}
		}
		return (Message[]) receivedMsg.toArray(new Message[receivedMsg.size()]);

	}

}

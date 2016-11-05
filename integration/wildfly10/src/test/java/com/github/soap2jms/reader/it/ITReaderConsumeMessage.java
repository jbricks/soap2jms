package com.github.soap2jms.reader.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.soap2jms.reader.JmsReaderSoap;
import com.github.soap2jms.reader.ReaderSoap2Jms;
import com.github.soap2jms.reader.common.ws.RetrieveMessageResponseType;
import com.github.soap2jms.reader.common.ws.WsJmsMessageAndStatus;

public class ITReaderConsumeMessage {
	private static final Logger log = LoggerFactory.getLogger(ITReaderConsumeMessage.class);

	// Set up all the default values
	private static final String DEFAULT_CONTENT = "Hello, World!";
	private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String DEFAULT_DESTINATION = "jms/soap2jms.reader";
	private static final String DEFAULT_USERNAME = "test";
	private static final String DEFAULT_PASSWORD = "test1";
	private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
	private static final String WSDL_LOCATION = "http://localhost:8080/soap2jms-integration-wildfly10/jmsReaderSoap?wsdl";

	@Test
	public void testReadTextMessage() throws Exception {
		// send a message to a queue.
		sendMessage(1);
		// read the message with webservice
		JmsReaderSoap wsClient = new JmsReaderSoap(WSDL_LOCATION);
		ReaderSoap2Jms reader = wsClient.getReaderSOAP();

		// TODO: map jms/soap2jms.reader to java:/comp/env/soap2jms in project
		// project_customization (web.xml).
		RetrieveMessageResponseType messages = reader.retrieveMessages("soap2jms", null, 100);
		assertTrue("complete response", messages.isComplete());
		List<WsJmsMessageAndStatus> jmsMessages = messages.getS2JMessageAndStatus();
		assertEquals("Messages retrieved", 1, jmsMessages.size());
		final DataHandler messageBody = jmsMessages.get(0).getWsJmsMessage().getBody();
		assertNotNull("Message body deserialized", messageBody);
		assertEquals("message content", DEFAULT_CONTENT, IOUtils.toString(messageBody.getInputStream(), "UTF-8"));
		// acknowlege the message

		// the message is not in the queue
	}

	@Test
	public void testAcknowledgeMessage2Times() {
		// send a message to a queue.
		// read the message with webservice

		// acknowlege the message

		// acknowledge the message OK
	}

	@Test
	public void testReadObjectMessage() {
		// send a message to a queue.
		// read the message with webservice

		// acknowlege the message

		// acknowledge the message OK
	}

	@Test
	public void testReadMapMessage() {
		// send a message to a queue.
		// read the message with webservice

		// acknowlege the message

		// acknowledge the message OK
	}

	private void sendMessage(int count) throws NamingException {

		Context namingContext = null;

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

			String content = System.getProperty("message.content", DEFAULT_CONTENT);

			try (JMSContext context = connectionFactory.createContext(userName, password)) {
				log.info("Sending " + count + " messages with content: " + content);
				// Send the specified number of messages
				for (int i = 0; i < count; i++) {
					context.createProducer().send(destination, content);
				}

				/*
				 * // Create the JMS consumer JMSConsumer consumer =
				 * context.createConsumer(destination); // Then receive the same
				 * number of messages that were sent for (int i = 0; i < count;
				 * i++) { String text = consumer.receiveBody(String.class,
				 * 5000); log.info("Received message with content " + text); }
				 */
			}
		} finally {
			if (namingContext != null) {
				try {
					namingContext.close();
				} catch (NamingException e) {
					log.error(e.getMessage());
				}
			}
		}
	}

}

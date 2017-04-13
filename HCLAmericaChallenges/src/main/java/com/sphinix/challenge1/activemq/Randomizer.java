package com.sphinix.challenge1.activemq;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.sphinix.util.Constants;

/**
 * @author Abhi
 * 
 *         This class responsibility is to send the numbers to JMS Queue
 */
public class Randomizer {

	final static Logger logger = Logger.getLogger(Randomizer.class);

	/**
	 * This method will sends the message to JMS
	 */
	public void sendMessage() {

		Connection connection = null;
		ConnectionFactory connectionFactory = null;
		Session session = null;
		MessageProducer producer = null;

		try {
			// Obtain a JNDI connection
			InitialContext jndi = new InitialContext();

			// Look up a JMS connection factory
			connectionFactory = (ConnectionFactory) jndi.lookup(Constants.CONNECTION_FACTORY);

			// Check whether we got the connectionFactory
			if (connectionFactory != null) {

				// Getting JMS connection from the server and starting it
				connection = connectionFactory.createConnection();

				logger.debug(" connection :: " + connection);

				if (connection != null) {

					connection.start();

					// JMS messages are sent and received using a Session.
					session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

					Destination destination = (Destination) jndi.lookup(Constants.MESSAGE_QUEUE);

					if (session != null) {
						// MessageProducer is used for sending messages (as opposed
						// to MessageConsumer which is used for receiving them)
						producer = session.createProducer(destination);

						// generate a random number
						int num = new Random().nextInt(1000) + 1;

						// Creating object message
						ObjectMessage message = session.createObjectMessage(new Integer(num));

						// Here we are sending the message!
						producer.send(message);

						logger.debug("Sent number '" + message.getObject() + "'");

					} else {
						logger.debug(" Session object is null");
					}
				} else {
					logger.debug(" Connection object is null");
				}

			}
		} catch (JMSException e) {
			logger.error(" JMSException : " + e.getMessage());
			e.printStackTrace();
		} catch (NamingException e) {
			logger.error(" NamingException : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(" Exception : " + e.getMessage());
			e.printStackTrace();
		} finally {
			logger.error("Closing connection");
			try {
				if (connection != null) {
					connection.stop();
					connection.close();
				}
				if (session != null) {
					session.close();
				}
				if (producer != null) {
					producer.close();
				}
			} catch (JMSException e) {
				logger.error(" Exception :" + e.getMessage());
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		Randomizer randomizer = new Randomizer();
		randomizer.sendMessage();
	}
	
}
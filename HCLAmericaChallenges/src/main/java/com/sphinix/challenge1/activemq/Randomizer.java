package com.sphinix.challenge1.activemq;

import java.util.Random;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.BasicConfigurator;

public class Randomizer {

	public Randomizer() throws JMSException, NamingException {

		// Obtain a JNDI connection
		InitialContext jndi = new InitialContext();

		// Look up a JMS connection factory
		ConnectionFactory conFactory = (ConnectionFactory) jndi.lookup("connectionFactory");
		Connection connection;

		// Getting JMS connection from the server and starting it
		connection = conFactory.createConnection();
		try {
			connection.start();

			// JMS messages are sent and received using a Session.
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = (Destination) jndi.lookup("MessageQueue");

			// MessageProducer is used for sending messages (as opposed
			// to MessageConsumer which is used for receiving them)
			MessageProducer producer = session.createProducer(destination);

			// We will send a small text message saying 'Hello World!'
			
			 Random rand = new Random(); // generate a random number
		     int num = rand.nextInt(1000) + 1;
			
			ObjectMessage message = session.createObjectMessage(new Integer(num));

			// Here we are sending the message!
			producer.send(message);
			System.out.println("Sent number '" + message.getObject() + "'");
		} finally {
			connection.close();
		}
	}

	public static void main(String[] args) throws JMSException {
		try {
			BasicConfigurator.configure();
			new Randomizer();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
}
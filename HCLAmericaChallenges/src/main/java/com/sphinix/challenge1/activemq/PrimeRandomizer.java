package com.sphinix.challenge1.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.sphinix.util.Constants;

public class PrimeRandomizer {

	final static Logger logger = Logger.getLogger(PrimeRandomizer.class);

	/**
	 * This method will receive the message from JMS and will check the number is prime or not
	 */
	public void receiveMessage() {

		Connection connection = null;

		try {
			// Getting JMS connection from the server
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			
			connection = connectionFactory.createConnection();
			
			if(connection!=null) {
				
				logger.debug(" Got Connection .....");
				
				// Creating session for seding messages
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				// Getting the queue
				Destination destination = session.createQueue(Constants.MESSAGE_QUEUE);

				// MessageConsumer is used for receiving (consuming) messages
				MessageConsumer consumer = session.createConsumer(destination);
				
				consumer.setMessageListener(new MessageListener() {
					
					@Override
				    public void onMessage(Message message) {
				      try {
				    	  	// There are many types of Message and ObjectMessage is just one of them. Producer sent us a ObjectMessage
							if (message instanceof ObjectMessage) {
								
								ObjectMessage objMessage = (ObjectMessage) message;
								
								logger.debug("Received number '" + objMessage.getObject() + "'");

								if(objMessage!=null && objMessage.getObject()!=null) {
							
									if (isPrime(new Integer(objMessage.getObject().toString()).intValue())) {
										logger.debug(objMessage.getObject() + " is a prime number ");
									} else {
										logger.debug(objMessage.getObject() + " is not a prime number ");
									}
									objMessage.acknowledge();
								}
							}

				      }
				      catch (JMSException e) {
				    	  logger.error("Error reading message");
				    	  e.printStackTrace();
				      }
				    }
				  });
				}
			
			connection.start();

		} catch (JMSException e) {
			logger.error(" JMSException : "+ e.getMessage());
			e.printStackTrace();
		} finally {
			logger.debug(" Closing connection");
			if(connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}


	/**
	 * This method will checks whether the number is prime or not
	 * @param number
	 * @return
	 */
	private boolean isPrime(int number) {
		// check if n is a multiple of 2
		if (number % 2 == 0)
			return false;
		// if not, then just check the odds
		for (int i = 3; i * i <= number; i += 2) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

	public static void main(String[] args)  {
		PrimeRandomizer primeRandomizer = new PrimeRandomizer();
		primeRandomizer.receiveMessage();
	}

	
}

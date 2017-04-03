package com.sphinix.challenge1.activemq;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

public class PrimeRandomizer {
	// URL of the JMS server
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	// Name of the queue we will receive messages from
	private static String subject = "MessageQueue";

	public static void main(String[] args) throws JMSException {
		BasicConfigurator.configure();
		// Getting JMS connection from the server
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		// Creating session for seding messages
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Getting the queue
		Destination destination = session.createQueue(subject);

		// MessageConsumer is used for receiving (consuming) messages
		MessageConsumer consumer = session.createConsumer(destination);

		
		
		// Here we receive the message.
		// By default this call is blocking, which means it will wait
		// for a message to arrive on the queue.
		Message message = consumer.receive();

		// There are many types of Message and ObjectMessage
		// is just one of them. Producer sent us a ObjectMessage
		// so we must cast to it to get access to its .getObject()
		// method.
		if (message instanceof ObjectMessage) {
			ObjectMessage number = (ObjectMessage) message;
			System.out.println("Received number '" + number.getObject() + "'");
			
			if(isPrime(new Integer(number.getObject().toString()).intValue())) {
				System.out.println(number.getObject() + " is a prime number ");
			} else {
				System.out.println(number.getObject() + " is not a prime number ");
			}
			
		}
	
		message.acknowledge();
		connection.close();
	}
	
	//checks whether an int is prime or not.
	static boolean isPrime(int n) {
	    //check if n is a multiple of 2
	    if (n%2==0) return false;
	    //if not, then just check the odds
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}
}

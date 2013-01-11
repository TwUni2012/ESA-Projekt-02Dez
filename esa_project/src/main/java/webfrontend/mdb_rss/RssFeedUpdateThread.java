package webfrontend.mdb_rss;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RssFeedUpdateThread extends Thread {
	
    private QueueConnectionFactory queueFactory;
    private Queue queue;

    public RssFeedUpdateThread() throws NamingException {
        final InitialContext context = new InitialContext();
        queueFactory = (QueueConnectionFactory) context.lookup("QueueFactory");
        queue = (Queue)context.lookup("RSSQueue");
        context.close();    	
	}
	
    /*
     * noch in testphase, feed-urls müssen aus der jcombobox mit den urls der feeds geladen werden
     */
	@Override
	public void run() {
//		while(true)
		{
	        try {
				final QueueConnection queueConnection = queueFactory.createQueueConnection();
				final QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
				final MessageProducer producer = queueSession.createProducer(queue);
				final TextMessage msg = queueSession.createTextMessage();

				/*
				 * send the feed urls to the mdb
				 */
				final String content = "http://newsfeed.zeit.de/index";
				msg.setText(content);
				producer.send(msg);

				producer.close();
				queueSession.close();
				queueConnection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
	        try {
				final QueueConnection queueConnection = queueFactory.createQueueConnection();
				final QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
				final MessageProducer producer = queueSession.createProducer(queue);
				final TextMessage msg = queueSession.createTextMessage();

				/*
				 * send the feed urls to the mdb
				 */
				final String content = "http://www.bundesliga.de/rss/de/rss_news.xml";
				msg.setText(content);
				producer.send(msg);

				producer.close();
				queueSession.close();
				queueConnection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		}
	}

}

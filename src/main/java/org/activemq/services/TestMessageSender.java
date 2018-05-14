package org.activemq.services;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

/**
 * The TestMessageSender class uses the injected JMSTemplate to send a message
 * to a specified Queue. In our case we're sending messages to 'processedQueue'
 */
@Service
public class TestMessageSender {
    private JmsTemplate jmsTemplate;
    private Queue testQueue;
    private static final Logger LOGGER = Logger.getLogger(TestMessageSender.class);

    public void sendMessage(String message) {
        LOGGER.debug("About to put message on queue. Queue[" + jmsTemplate.toString() + "] Message[" + message + "]");
        jmsTemplate.convertAndSend(testQueue, message);
    }

    public void setJmsTemplate(JmsTemplate tmpl) {
        this.jmsTemplate = tmpl;
    }

    public void setTestQueue(Queue queue) {
        this.testQueue = queue;
    }
}

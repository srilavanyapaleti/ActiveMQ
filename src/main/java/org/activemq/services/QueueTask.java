package org.activemq.services;

import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import java.util.concurrent.Callable;

/**
 * Indicates an object that is submitted to the listener as a separate task.
 */
public class QueueTask implements Callable {

    private static final Logger LOGGER = Logger.getLogger(QueueTask.class);
    private Message message;

    QueueTask(final Message message) {
        this.message = message;
    }

    public Object call() throws JMSException {
        if (message instanceof TextMessage) {
            try {
                String msgText = ((TextMessage) message).getText();
                LOGGER.debug("About to process asynchronous messages : "+ msgText);
                throw new RuntimeException("Message thrown to redeliver!");
            } catch (JMSException jsme) {
                LOGGER.error("An error occurred extracting message {}", jsme);
                //throw new JMSException("An error occurred extracting message - Throwing JMSException");
            }
        } else {
            String errMsg = "Message is not of expected type TextMessage";
            LOGGER.error(errMsg);
            throw new JMSException(errMsg);
        }
        return null;
    }
}

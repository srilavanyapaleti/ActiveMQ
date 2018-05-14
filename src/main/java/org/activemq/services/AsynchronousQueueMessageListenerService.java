package org.activemq.services;

import org.apache.log4j.Logger;
import org.springframework.util.ErrorHandler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Service handles the queue that processes messages asynchronously.
 */
class AsynchronousQueueMessageListenerService implements MessageListener, ErrorHandler {

    private TestMessageSender messageSender;
    private static final Logger LOGGER = Logger.getLogger(AsynchronousQueueMessageListenerService.class);

    public void onMessage(Message message) {
        LOGGER.debug("Received message from queue [" + message + "]");
        if (message instanceof TextMessage) {
            try {
                String msgText = ((TextMessage) message).getText();
                LOGGER.debug("About to process asynchronous messages : " + msgText);
                throw new RuntimeException("Message thrown to redeliver!");
            } catch (JMSException jmsEx_p) {
                String errMsg = "An error occurred extracting message";
                LOGGER.error(errMsg, jmsEx_p);
            }
        } else {
            String errMsg = "Message is not of expected type TextMessage";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }
    }

    /**
     * Sets test message sender.
     *
     * @param messageSender the message sender
     */
    public void setTestMessageSender(TestMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void handleError(final Throwable throwable) {
        LOGGER.error("Redelivering the message to the queue", throwable);
    }
}

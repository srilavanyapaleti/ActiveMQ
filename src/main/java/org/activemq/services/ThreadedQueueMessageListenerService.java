package org.activemq.services;

import org.apache.log4j.Logger;
import org.springframework.util.ErrorHandler;

import javax.annotation.PreDestroy;

import javax.jms.Message;
import javax.jms.MessageListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Service handles the queue that processes messages asynchronously using separate threads.
 */
class ThreadedQueueMessageListenerService implements MessageListener, ErrorHandler {

    private TestMessageSender messageSender;
    private static final Logger LOGGER = Logger.getLogger(ThreadedQueueMessageListenerService.class);

    private ExecutorService executorService;

    @PreDestroy
    void preDestroy() {
        executorService.shutdown();
    }

    public void onMessage(Message message) {
        LOGGER.debug("Received message from queue [" + message + "]");
        int numThreads = 10;
        executorService = Executors.newFixedThreadPool(numThreads);
        QueueTask task = new QueueTask(message);
        Future<Boolean> future = executorService.submit(task);
        try {
            if (future.get()) {
                LOGGER.debug("Task Execution Successful!");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread is interrupted!");
        } catch (ExecutionException e) {
            throw new RuntimeException("exception", e);
        }
    }

    public void setTestMessageSender(TestMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void handleError(final Throwable throwable) {
        LOGGER.error("Redelivering the message to the queue", throwable);
    }

    /** Simple struct to hold all the date related to a ping. */
    private static final class PingResult {
        String URL;
        Boolean SUCCESS;
        Long TIMING;

        @Override public String toString() {
            return "Result:" + SUCCESS + " " + TIMING + " msecs " + URL;
        }
    }
}

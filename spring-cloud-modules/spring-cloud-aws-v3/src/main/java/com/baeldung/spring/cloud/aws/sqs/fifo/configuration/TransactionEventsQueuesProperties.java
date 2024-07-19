package com.baeldung.spring.cloud.aws.sqs.fifo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "events.queues.fifo")
public class TransactionEventsQueuesProperties {

    private String transactionsQueue;

    private String slowQueue;

    private String failureQueue;

    public String getTransactionsQueue() {
        return transactionsQueue;
    }

    public void setTransactionsQueue(String transactionsQueue) {
        this.transactionsQueue = transactionsQueue;
    }

    public String getSlowQueue() {
        return slowQueue;
    }

    public void setSlowQueue(String slowQueue) {
        this.slowQueue = slowQueue;
    }

    public String getFailureQueue() {
        return failureQueue;
    }

    public void setFailureQueue(String failureQueue) {
        this.failureQueue = failureQueue;
    }
}

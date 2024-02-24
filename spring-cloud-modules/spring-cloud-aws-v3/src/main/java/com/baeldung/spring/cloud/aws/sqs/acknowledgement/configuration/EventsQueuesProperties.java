package com.baeldung.spring.cloud.aws.sqs.acknowledgement.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "events.queues")
public class EventsQueuesProperties {

    private String orderProcessingRetryQueue;

    private String orderProcessingAsyncQueue;

    private String orderProcessingNoRetriesQueue;

    public String getOrderProcessingRetryQueue() {
        return orderProcessingRetryQueue;
    }

    public void setOrderProcessingRetryQueue(String orderProcessingRetryQueue) {
        this.orderProcessingRetryQueue = orderProcessingRetryQueue;
    }

    public String getOrderProcessingAsyncQueue() {
        return orderProcessingAsyncQueue;
    }

    public void setOrderProcessingAsyncQueue(String orderProcessingAsyncQueue) {
        this.orderProcessingAsyncQueue = orderProcessingAsyncQueue;
    }

    public String getOrderProcessingNoRetriesQueue() {
        return orderProcessingNoRetriesQueue;
    }

    public void setOrderProcessingNoRetriesQueue(String orderProcessingNoRetriesQueue) {
        this.orderProcessingNoRetriesQueue = orderProcessingNoRetriesQueue;
    }
}
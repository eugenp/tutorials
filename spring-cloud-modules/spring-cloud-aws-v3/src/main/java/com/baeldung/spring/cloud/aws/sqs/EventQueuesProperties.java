package com.baeldung.spring.cloud.aws.sqs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "events.queues")
public class EventQueuesProperties {

    private String userCreatedByNameQueue;
    private String userCreatedFifoQueue;
    private String userCreatedEventTypeQueue;

    public String getUserCreatedByNameQueue() {
        return userCreatedByNameQueue;
    }

    public void setUserCreatedByNameQueue(String userCreatedByNameQueue) {
        this.userCreatedByNameQueue = userCreatedByNameQueue;
    }

    public String getUserCreatedFifoQueue() {
        return userCreatedFifoQueue;
    }

    public void setUserCreatedFifoQueue(String userCreatedFifoQueue) {
        this.userCreatedFifoQueue = userCreatedFifoQueue;
    }

    public String getUserCreatedEventTypeQueue() {
        return userCreatedEventTypeQueue;
    }

    public void setUserCreatedEventTypeQueue(String userCreatedEventTypeQueue) {
        this.userCreatedEventTypeQueue = userCreatedEventTypeQueue;
    }
}

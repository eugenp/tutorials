package com.baeldung.spring.cloud.aws.sqs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "events.queues")
public class EventQueuesProperties {

    private String userCreatedByNameQueue;
    private String userCreatedQueue;
    private String userCreatedEventTypeQueue;

    public String getUserCreatedByNameQueue() {
        return userCreatedByNameQueue;
    }

    public void setUserCreatedByNameQueue(String userCreatedByNameQueue) {
        this.userCreatedByNameQueue = userCreatedByNameQueue;
    }

    public String getUserCreatedQueue() {
        return userCreatedQueue;
    }

    public void setUserCreatedQueue(String userCreatedQueue) {
        this.userCreatedQueue = userCreatedQueue;
    }

    public String getUserCreatedEventTypeQueue() {
        return userCreatedEventTypeQueue;
    }

    public void setUserCreatedEventTypeQueue(String userCreatedEventTypeQueue) {
        this.userCreatedEventTypeQueue = userCreatedEventTypeQueue;
    }
}

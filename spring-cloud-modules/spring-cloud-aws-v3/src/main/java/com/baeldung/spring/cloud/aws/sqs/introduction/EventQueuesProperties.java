package com.baeldung.spring.cloud.aws.sqs.introduction;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "events.queues")
public class EventQueuesProperties {

    private String userCreatedByNameQueue;
    private String userCreatedRecordQueue;
    private String userCreatedEventTypeQueue;

    public String getUserCreatedByNameQueue() {
        return userCreatedByNameQueue;
    }

    public void setUserCreatedByNameQueue(String userCreatedByNameQueue) {
        this.userCreatedByNameQueue = userCreatedByNameQueue;
    }

    public String getUserCreatedRecordQueue() {
        return userCreatedRecordQueue;
    }

    public void setUserCreatedRecordQueue(String userCreatedRecordQueue) {
        this.userCreatedRecordQueue = userCreatedRecordQueue;
    }

    public String getUserCreatedEventTypeQueue() {
        return userCreatedEventTypeQueue;
    }

    public void setUserCreatedEventTypeQueue(String userCreatedEventTypeQueue) {
        this.userCreatedEventTypeQueue = userCreatedEventTypeQueue;
    }
}

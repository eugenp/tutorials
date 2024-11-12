package com.baeldung.kafka.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "notification", createIndex = true)
public class NotificationEntity {

    @Id
    private int userId;

    private String message;

    private int recipientId;

    public NotificationEntity() {
    }

    public NotificationEntity(int userId, String message, int recipientId) {
        this.userId = userId;
        this.message = message;
        this.recipientId = recipientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" + "user_id=" + userId + ", message='" + message + '\'' + ", recipient_id=" + recipientId + '}';
    }
}

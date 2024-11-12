package com.baeldung.kafka.model;

public class NotificationModel {

    private int userId;

    private String message;

    private int recipientId;

    public NotificationModel(int userId, String message, int recipientId) {
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
}

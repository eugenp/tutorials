package com.baeldung.kafka.model;

public class NotificationModel {

    private int userId;

    private String message;

    private int recipientId;

    public NotificationModel() {
    }

    public NotificationModel(@NotNull int userId, @NotNull String message, @NotNull int recipientId) {
        this.userId = userId;
        this.message = message;
        this.recipientId = recipientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(@NotNull int userId) {
        this.userId = userId;
    }

    public @NotNull String getMessage() {
        return message;
    }

    public void setMessage(@NotNull String message) {
        this.message = message;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(@NotNull int recipientId) {
        this.recipientId = recipientId;
    }
}

package com.baeldung.flink.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

import java.time.LocalDateTime;

@JsonSerialize
public class InputMessage {
    String sender;
    String recipient;
    LocalDateTime sentAt;
    String message;

    public InputMessage() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InputMessage(String sender, String recipient, LocalDateTime sentAt, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.sentAt = sentAt;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InputMessage message1 = (InputMessage) o;
        return Objects.equal(sender, message1.sender) && Objects.equal(recipient, message1.recipient) && Objects.equal(sentAt, message1.sentAt) && Objects.equal(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sender, recipient, sentAt, message);
    }
}

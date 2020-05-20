package com.baeldung.hexagonal.domain;

public class MessageResponse {
    private boolean delivered;
    private String messageId;

    public MessageResponse(boolean delivered, String messageId) {
        this.delivered = delivered;
        this.messageId = messageId;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}

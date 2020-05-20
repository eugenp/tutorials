package com.baeldung.hexagonal.domain;

public class MessageRequest {
    private String body;
    private String dest;
    private String source;
    private String messageId;

    public MessageRequest(String body, String dest, String source, String messageId){
        this.body = body;
        this.dest = dest;
        this.source = source;
        this.messageId = messageId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}

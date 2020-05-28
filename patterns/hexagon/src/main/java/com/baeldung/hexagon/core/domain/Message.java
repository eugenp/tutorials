package com.baeldung.hexagon.core.domain;

public class Message {

    private String id;
    private String sender;
    private String receiver;
    private String content;

    public Message(String id, String sender, String receiver, String content) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }
}

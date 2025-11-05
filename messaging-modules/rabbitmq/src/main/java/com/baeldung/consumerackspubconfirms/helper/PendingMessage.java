package com.baeldung.consumerackspubconfirms.helper;

public class PendingMessage {

    private int tries;
    private final String body;

    public PendingMessage(String body) {
        this.body = body;
    }

    public int incrementTries() {
        return ++this.tries;
    }

    public int getTries() {
        return tries;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s", tries, body);
    }
}

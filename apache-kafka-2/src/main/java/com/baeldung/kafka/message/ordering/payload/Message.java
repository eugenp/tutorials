package com.baeldung.kafka.message.ordering.payload;

import java.util.Random;

public class Message implements Comparable<Message> {
    private long insertPosition;
    private long messageId;

    public Message(){

    }

    public Message(long insertPosition, long messageId) {
        this.insertPosition = insertPosition;
        this.messageId = messageId;
    }

    public long getInsertPosition() {
        return insertPosition;
    }

    public long getMessageId() {
        return messageId;
    }

    @Override
    public int compareTo(Message other) {
        return Long.compare(this.messageId, other.messageId);
    }

    public static long getRandomMessageId() {
        Random rand = new Random();
        return rand.nextInt(1000);
    }
}


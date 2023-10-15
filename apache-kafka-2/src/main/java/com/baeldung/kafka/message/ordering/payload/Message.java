package com.baeldung.kafka.message.ordering.payload;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Message implements Comparable<Message> {
    private long insertPosition;
    private long messageId;

    public Message(){

    }

    //Required for Kafka Serialization and Deserialization
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Message)) {
            return false;
        }
        Message message = (Message) obj;
        return this.messageId == message.getMessageId() && this.insertPosition == message.getInsertPosition();
    }

    public static long getRandomMessageId() {
        Random rand = new Random();
        return ThreadLocalRandom.current().nextInt(1000);
    }
}


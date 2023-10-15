package com.baeldung.kafka.message.ordering.payload;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Message implements Comparable<Message> {
    private String partitionKey;
    private long messageId;

    public Message(){

    }

    //Required for Kafka Serialization and Deserialization
    public Message(String partitionKey, long messageId) {
        this.partitionKey = partitionKey;
        this.messageId = messageId;
    }

    public String getPartitionKey() {
        return partitionKey;
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
        return this.messageId == message.getMessageId() && Objects.equals(this.partitionKey, message.getPartitionKey());
    }

    public static long getRandomMessageId() {
        Random rand = new Random();
        return ThreadLocalRandom.current().nextInt(1000);
    }
}


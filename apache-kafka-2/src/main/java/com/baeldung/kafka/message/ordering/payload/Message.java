package com.baeldung.kafka.message.ordering.payload;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Message implements Comparable<Message> {
    private long partitionKey;
    private long applicationIdentifier;

    public Message(){

    }

    //Required for Kafka Serialization and Deserialization
    public Message(long partitionKey, long applicationIdentifier) {
        this.partitionKey = partitionKey;
        this.applicationIdentifier = applicationIdentifier;
    }

    public long getPartitionKey() {
        return partitionKey;
    }

    public long getApplicationIdentifier() {
        return applicationIdentifier;
    }

    @Override
    public int compareTo(Message other) {
        return Long.compare(this.partitionKey, other.partitionKey);
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
        return this.applicationIdentifier == message.getApplicationIdentifier() && Objects.equals(this.partitionKey, message.getPartitionKey());
    }

    public static long getRandomApplicationIdentifier() {
        Random rand = new Random();
        return ThreadLocalRandom.current().nextInt(1000);
    }
}


package com.baeldung.partitioningstrategy;

public class ReceivedMessage {
    private final String key;
    private final String message;
    private final int partition;

    public ReceivedMessage(String key, String message, int partition) {
        this.key = key;
        this.message = message;
        this.partition = partition;
    }

    @Override
    public String toString() {
        return "Key: " + key + " - Message: " + message + " - Partition: " + partition;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public int getPartition() {
        return partition;
    }
}

package com.baeldung.hexagonal.core;

public class OutOfStorageException extends RuntimeException {

    private final int storageQuantity;

    private final int requestedQuantity;

    public OutOfStorageException(int storageQuantity, int requestedQuantity) {
        this.storageQuantity = storageQuantity;
        this.requestedQuantity = requestedQuantity;
    }

    public int getStorageQuantity() {
        return storageQuantity;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }
}

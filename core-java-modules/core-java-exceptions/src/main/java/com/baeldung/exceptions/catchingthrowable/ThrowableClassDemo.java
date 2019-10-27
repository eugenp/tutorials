package com.baeldung.exceptions.catchingthrowable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ThrowableClassDemo {

    class CapacityException extends Exception {
        CapacityException(String message) {
            super(message);
        }
    }

    public void addIDsToStorage(int capacity, Set<String> storage) throws CapacityException {
        if (capacity < 0) {
            throw new CapacityException("Negative capacity not allowed");
        }
        int count = 0;
        boolean capacityNotReached = true;
        while (capacityNotReached) {
            storage.add(UUID.randomUUID().toString());
            count++;
            capacityNotReached = count < capacity;
        }
    }
}

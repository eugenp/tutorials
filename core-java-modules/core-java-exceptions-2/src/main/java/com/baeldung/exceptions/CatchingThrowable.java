package com.baeldung.exceptions;

import java.util.Set;
import java.util.UUID;

public class CatchingThrowable  {

    class CapacityException extends Exception {
        CapacityException(String message) {
            super(message);
        }
    }

    class StorageAPI {

        public void addIDsToStorage(int capacity, Set<String> storage) throws CapacityException {
            if (capacity < 1) {
                throw new CapacityException("Capacity of less than 1 is not allowed");
            }
            int count = 0;
            while (count < capacity) {
                storage.add(UUID.randomUUID().toString());
                count++;
            }
        }

        // other methods go here ...
    }

    public void add(StorageAPI api, int capacity, Set<String> storage) {
        try {
            api.addIDsToStorage(capacity, storage);
        } catch (Throwable throwable) {
            // do something here
        }
    }

}

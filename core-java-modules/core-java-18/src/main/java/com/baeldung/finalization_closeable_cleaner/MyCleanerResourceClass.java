package com.baeldung.finalization_closeable_cleaner;

import java.lang.ref.Cleaner;

public class MyCleanerResourceClass implements AutoCloseable {
    private static Resource resource;

    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;

    public MyCleanerResourceClass() {
        resource = new Resource();
        this.cleanable = cleaner.register(this, new CleaningState());
    }

    public void useResource() {
        // using the resource here
        resource.use();
    }

    @Override
    public void close() {
        // perform actions to close all underlying resources
        this.cleanable.clean();
    }

    static class CleaningState implements Runnable {
        CleaningState() {
            // constructor
        }

        @Override
        public void run() {
            // some cleanup action
            System.out.println("Cleanup done");
        }
    }

    static class Resource {
        void use() {
            System.out.println("Using the resource");
        }

        void close() {
            System.out.println("Cleanup done");
        }
    }
}

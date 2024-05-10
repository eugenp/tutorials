package com.baeldung.sample.singleton;

public final class ThreadSafeSingleInstance {

    private static volatile ThreadSafeSingleInstance instance = null;

    private ThreadSafeSingleInstance() {}

    public static ThreadSafeSingleInstance getInstance() {
        if (instance == null) {
            synchronized(ThreadSafeSingleInstance.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleInstance();
                }
            }
        }
        return instance;
    }

    public String getValue() {
        return "test";
    }

}

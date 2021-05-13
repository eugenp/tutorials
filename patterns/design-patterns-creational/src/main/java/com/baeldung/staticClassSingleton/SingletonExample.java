package com.baeldung.staticClassSingleton;

import java.util.concurrent.atomic.AtomicLong;

public class SingletonExample {
    private static SingletonExample INSTANCE;
    private AtomicLong counter;

    public static SingletonExample getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonExample();
        }
        return INSTANCE;
    }

    public long counter(long times){
        return counter.addAndGet(times);
    }

    private SingletonExample() {
        counter = new AtomicLong();
    }
}

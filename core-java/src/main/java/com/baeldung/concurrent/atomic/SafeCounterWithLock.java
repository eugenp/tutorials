package com.baeldung.concurrent.atomic;

public class SafeCounterWithLock {
    private volatile int counter;
    
    public int getValue() {
        return counter;
    }
    
    public synchronized void increment() {
        counter++;
    }
}

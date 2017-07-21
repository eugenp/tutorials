package com.baeldung.concurrent.atomic;

public class SafeCounterWithLock {
    int counter;
    
    public int getValue() {
        return counter;
    }
    
    public synchronized void increment() {
        counter++;
    }
}

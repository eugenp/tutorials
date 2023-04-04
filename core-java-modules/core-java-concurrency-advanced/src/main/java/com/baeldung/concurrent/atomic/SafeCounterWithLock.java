package com.baeldung.concurrent.atomic;

public class SafeCounterWithLock {
    private int counter;
    
    int getValue() {
        return counter;
    }
    
    synchronized void increment() {
        counter++;
    }
}

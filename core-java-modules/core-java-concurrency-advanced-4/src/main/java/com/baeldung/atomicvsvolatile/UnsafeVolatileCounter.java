package com.baeldung.atomicvsvolatile;

public class UnsafeVolatileCounter {

    private volatile int counter;
    
    public int getValue() {
        return counter;
    }
    
    public void increment() {
        counter++;
    }
}

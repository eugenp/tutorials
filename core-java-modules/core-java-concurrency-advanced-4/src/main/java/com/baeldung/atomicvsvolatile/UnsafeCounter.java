package com.baeldung.atomicvsvolatile;

public class UnsafeCounter {

    private int counter;
    
    public int getValue() {
        return counter;
    }
    
    public void increment() {
        counter++;
    }
}

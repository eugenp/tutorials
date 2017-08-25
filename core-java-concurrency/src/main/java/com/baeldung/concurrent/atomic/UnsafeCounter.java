package com.baeldung.concurrent.atomic;

public class UnsafeCounter {
    int counter;
    
    public int getValue() {
        return counter;
    }
    
    public void increment() {
        counter++;
    }
}

package com.baeldung.concurrent.atomic;

public class UnsafeCounter {
    private int counter;
    
    int getValue() {
        return counter;
    }
    
    void increment() {
        counter++;
    }
}

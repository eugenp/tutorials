package com.baeldung.lombok.locked;

import lombok.Locked;

public class Counter {

    private int counter = 0;

    @Locked
    public void increment() {
        counter++;
    }

    @Locked.Read
    public int get() {
        return counter;
    }
}

package com.baeldung.concurrent.mutex;

public class SequenceGenerator {
    private int currentValue = 0;

    public int getNextSequence() throws InterruptedException {
        currentValue = currentValue + 1;
        Thread.sleep(500);
        return currentValue;
    }
}

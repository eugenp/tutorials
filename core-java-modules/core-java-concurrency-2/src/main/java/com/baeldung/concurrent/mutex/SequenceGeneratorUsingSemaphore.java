package com.baeldung.concurrent.mutex;

import java.util.concurrent.Semaphore;

public class SequenceGeneratorUsingSemaphore extends SequenceGenerator {

    private Semaphore mutex = new Semaphore(1);

    @Override
    public int getNextSequence() {
        try {
            mutex.acquire();
            return super.getNextSequence();
        } catch (InterruptedException e) {
            throw new RuntimeException("Exception in critical section.", e);
        } finally {
            mutex.release();
        }
    }

}

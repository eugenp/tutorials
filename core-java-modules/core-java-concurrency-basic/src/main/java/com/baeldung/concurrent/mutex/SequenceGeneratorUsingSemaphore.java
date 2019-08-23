package com.baeldung.concurrent.mutex;

import java.util.concurrent.Semaphore;

public class SequenceGeneratorUsingSemaphore extends SequenceGenerator {

    private Semaphore mutex = new Semaphore(1);

    @Override
    public int getNextSequence() throws InterruptedException {
        try {
            mutex.acquire();
            return super.getNextSequence();
        } finally {
            mutex.release();
        }
    }
}

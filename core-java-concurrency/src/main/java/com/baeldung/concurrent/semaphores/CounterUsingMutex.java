package com.baeldung.concurrent.semaphores;

import java.util.concurrent.Semaphore;

class CounterUsingMutex {
    public static final long DELAY = 200;
    private final Semaphore mutex;
    private int count;

    CounterUsingMutex() {
        mutex = new Semaphore(1);
        count = 0;
    }

    void increase() throws InterruptedException {
        mutex.acquire();
        this.count = this.count + 1;
        Thread.sleep(DELAY);
        mutex.release();

    }

    int getCount() {
        return this.count;
    }

    boolean hasQueuedThreads() {
        return mutex.hasQueuedThreads();
    }

}

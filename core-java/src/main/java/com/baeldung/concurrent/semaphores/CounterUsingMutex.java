package com.baeldung.concurrent.semaphores;

import java.util.concurrent.Semaphore;

public class CounterUsingMutex {

    private final Semaphore mutex;
    private int count;

    public CounterUsingMutex() {
        mutex = new Semaphore(1);
        count = 0;
    }

    public void increase() throws InterruptedException {
        mutex.acquire();
        this.count = this.count + 1;
        Thread.sleep(1000);
        mutex.release();

    }

    public int getCount() {
        return this.count;
    }

    public boolean hasQueuedThreads() {
        return mutex.hasQueuedThreads();
    }

}

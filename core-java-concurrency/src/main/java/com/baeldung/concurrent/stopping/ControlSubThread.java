package com.baeldung.concurrent.stopping;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlSubThread implements Runnable {

    private final Semaphore sem;
    private Thread worker;
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean stopped = new AtomicBoolean(true);

    public ControlSubThread() {
        this.sem = new Semaphore(0);
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
        this.sem.release();
    }

    public void interrupt() {
        stop();
        worker.interrupt();
    }

    boolean isRunning() {
        return running.get();
    }

    boolean isStopped() {
        return stopped.get();
    }

    @Override
    public void run() {
        running.set(true);
        stopped.set(false);
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
            System.out.println("Thread was interrupted, Failed to complete operation");
        }
        stopped.set(true);
    }
}

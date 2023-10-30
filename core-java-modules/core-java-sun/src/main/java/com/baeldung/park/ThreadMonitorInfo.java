package com.baeldung.park;

import java.util.concurrent.locks.LockSupport;

public class ThreadMonitorInfo {
    private static final Object MONITOR = new Object();
    public static void main(String[] args) throws InterruptedException {
        final Thread waitingThread = new Thread(() -> {
            try {
                synchronized (MONITOR) {
                    MONITOR.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Waiting Thread");

        final Thread parkedThread = new Thread(LockSupport::park, "Parked Thread");

        waitingThread.start();
        parkedThread.start();

        waitingThread.join();
        parkedThread.join();
    }
}

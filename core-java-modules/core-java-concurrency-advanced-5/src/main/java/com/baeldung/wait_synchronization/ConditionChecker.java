package com.baeldung.wait_synchronization;

public class ConditionChecker {

    private volatile Boolean jobIsDone;
    private final Object lock = new Object();

    public void ensureCondition() {
        synchronized (lock) {
            while (!jobIsDone) {
                try {
                    lock.wait();
                } catch (InterruptedException e) { }
            }
        }
    }

    public void complete() {
        synchronized (lock) {
            jobIsDone = true;
            lock.notify();
        }
    }
}
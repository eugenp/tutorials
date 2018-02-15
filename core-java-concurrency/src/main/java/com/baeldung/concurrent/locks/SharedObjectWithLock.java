package com.baeldung.concurrent.locks;

import static java.lang.Thread.sleep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedObjectWithLock {

    public static final long DELAY_MILLIS = 200;

    private static final Logger LOG = LoggerFactory.getLogger(SharedObjectWithLock.class);

    private ReentrantLock lock = new ReentrantLock(true);

    private final Semaphore sem;

    public SharedObjectWithLock() {
        this(new Semaphore(1));
    }

    public SharedObjectWithLock(Semaphore sem) {
        this.sem = sem;
    }

    private int counter = 0;

    void perform() {

        lock.lock();
        sem.release();
        LOG.info("Thread - " + Thread.currentThread()
            .getName() + " acquired the lock");
        try {
            LOG.info("Thread - " + Thread.currentThread()
                .getName() + " processing");
            sem.acquire();
            counter++;
        } catch (Exception exception) {
            LOG.error(" Interrupted Exception ", exception);
        } finally {
            lock.unlock();
            LOG.info("Thread - " + Thread.currentThread()
                .getName() + " released the lock");
        }
    }

    private void performTryLock() {

        LOG.info("Thread - " + Thread.currentThread()
            .getName() + " attempting to acquire the lock");
        try {
            boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
            if (isLockAcquired) {
                try {
                    LOG.info("Thread - " + Thread.currentThread()
                        .getName() + " acquired the lock");

                    LOG.info("Thread - " + Thread.currentThread()
                        .getName() + " processing");
                    delay();
                } finally {
                    lock.unlock();
                    LOG.info("Thread - " + Thread.currentThread()
                        .getName() + " released the lock");

                }
            }
        } catch (InterruptedException exception) {
            LOG.error(" Interrupted Exception ", exception);
        }
        LOG.info("Thread - " + Thread.currentThread()
            .getName() + " could not acquire the lock");
    }

    public ReentrantLock getLock() {
        return lock;
    }

    boolean isLocked() {
        return lock.isLocked();
    }

    boolean hasQueuedThreads() {
        return lock.hasQueuedThreads();
    }

    int getCounter() {
        return counter;
    }

    void delay() throws InterruptedException {
        sleep(DELAY_MILLIS);
    }

    public static void main(String[] args) {

        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        service.execute(object::perform);
        service.execute(object::performTryLock);

        service.shutdown();

    }

}

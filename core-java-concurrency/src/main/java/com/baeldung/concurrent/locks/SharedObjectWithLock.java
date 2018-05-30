package com.baeldung.concurrent.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class SharedObjectWithLock {

    private static final Logger LOG = LoggerFactory.getLogger(SharedObjectWithLock.class);

    private ReentrantLock lock = new ReentrantLock(true);

    private int counter = 0;

    void perform() {

        lock.lock();
        LOG.info("Thread - " + Thread.currentThread().getName() + " acquired the lock");
        try {
            LOG.info("Thread - " + Thread.currentThread().getName() + " processing");
            counter++;
        } catch (Exception exception) {
            LOG.error(" Interrupted Exception ", exception);
        } finally {
            lock.unlock();
            LOG.info("Thread - " + Thread.currentThread().getName() + " released the lock");
        }
    }

    private void performTryLock() {

        LOG.info("Thread - " + Thread.currentThread().getName() + " attempting to acquire the lock");
        try {
            boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
            if (isLockAcquired) {
                try {
                    LOG.info("Thread - " + Thread.currentThread().getName() + " acquired the lock");

                    LOG.info("Thread - " + Thread.currentThread().getName() + " processing");
                    sleep(1000);
                } finally {
                    lock.unlock();
                    LOG.info("Thread - " + Thread.currentThread().getName() + " released the lock");

                }
            }
        } catch (InterruptedException exception) {
            LOG.error(" Interrupted Exception ", exception);
        }
        LOG.info("Thread - " + Thread.currentThread().getName() + " could not acquire the lock");
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

    public static void main(String[] args) {

        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        service.execute(object::perform);
        service.execute(object::performTryLock);

        service.shutdown();

    }

}

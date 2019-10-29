package com.baeldung.concurrent.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;

public class StampedLockDemo {
    private Map<String, String> map = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(StampedLockDemo.class);

    private final StampedLock lock = new StampedLock();

    public void put(String key, String value) throws InterruptedException {
        long stamp = lock.writeLock();

        try {
            logger.info(Thread.currentThread().getName() + " acquired the write lock with stamp " + stamp);
            map.put(key, value);
        } finally {
            lock.unlockWrite(stamp);
            logger.info(Thread.currentThread().getName() + " unlocked the write lock with stamp " + stamp);
        }
    }

    public String get(String key) throws InterruptedException {
        long stamp = lock.readLock();
        logger.info(Thread.currentThread().getName() + " acquired the read lock with stamp " + stamp);
        try {
            sleep(5000);
            return map.get(key);

        } finally {
            lock.unlockRead(stamp);
            logger.info(Thread.currentThread().getName() + " unlocked the read lock with stamp " + stamp);

        }

    }

    private String readWithOptimisticLock(String key) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();
        String value = map.get(key);

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                sleep(5000);
                return map.get(key);

            } finally {
                lock.unlock(stamp);
                logger.info(Thread.currentThread().getName() + " unlocked the read lock with stamp " + stamp);

            }
        }
        return value;
    }

    public static void main(String[] args) {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        StampedLockDemo object = new StampedLockDemo();

        Runnable writeTask = () -> {

            try {
                object.put("key1", "value1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable readTask = () -> {

            try {
                object.get("key1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable readOptimisticTask = () -> {

            try {
                object.readWithOptimisticLock("key1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        service.submit(writeTask);
        service.submit(writeTask);
        service.submit(readTask);
        service.submit(readOptimisticTask);

        service.shutdown();

    }

}

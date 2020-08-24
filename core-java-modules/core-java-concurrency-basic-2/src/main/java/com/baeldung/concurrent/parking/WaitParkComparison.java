package com.baeldung.concurrent.parking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

class WaitParkComparison {
    private static final Logger LOG = LoggerFactory.getLogger(WaitParkComparison.class);

    public void parkingThread throws InterruptedException {
        Object lockObject = new Object();
        Runnable task1 = () -> {
            synchronized (lockObject) {
                LOG.debug("Thread 1 is blocked") LockSupport.park();
                LOG.debug("Thread 1 resumed")
            }
        }; Thread thread1 = new Thread(task1);
        thread1.start();
        Runnable task2 = () -> {
            LOG.debug("Thread 2 running ");
            synchronized (lockObject) {
                LOG.debug("Thread 2 get lock");
                LockSupport.unpark(thread1);
            }
        };
        Thread thread2 = new Thread(task2);
        thread2.start();
    }
}

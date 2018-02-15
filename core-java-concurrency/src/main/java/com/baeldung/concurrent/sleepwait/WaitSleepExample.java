package com.baeldung.concurrent.sleepwait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Example of wait() and sleep() methods
 */
public class WaitSleepExample {

    private static final Logger LOG = LoggerFactory.getLogger(WaitSleepExample.class);

    private static final Object LOCK = new Object();

    public static void main(String... args) throws InterruptedException {
        sleepWaitInSynchronizedBlocks();
    }

    private static void sleepWaitInSynchronizedBlocks() throws InterruptedException {
        Thread.sleep(1000); // called on the thread
        LOG.debug("Thread '" + Thread.currentThread().getName() + "' is woken after sleeping for 1 second");

        synchronized (LOCK) {
            LOCK.wait(1000); // called on the object, synchronization required
            LOG.debug("Object '" + LOCK + "' is woken after waiting for 1 second");
        }
    }

}

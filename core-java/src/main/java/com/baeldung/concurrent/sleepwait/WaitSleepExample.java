package com.baeldung.concurrent.sleepwait;

/***
 * Example of wait() and sleep() methods
 */
public class WaitSleepExample {
    private static final Object LOCK = new Object();

    public static void main(String... args) throws InterruptedException {
        sleepWaitInSyncronizedBlocks();
    }

    private static void sleepWaitInSyncronizedBlocks() throws InterruptedException {
        Thread.sleep(1000); // called on the thread
        System.out.println("Thread '" + Thread.currentThread().getName() + "' is woken after sleeping for 1 second");

        synchronized (LOCK) {
            LOCK.wait(1000); // called on the object, synchronization required
            System.out.println("Object '" + LOCK + "' is woken after waiting for 1 second");
        }
    }
    
}

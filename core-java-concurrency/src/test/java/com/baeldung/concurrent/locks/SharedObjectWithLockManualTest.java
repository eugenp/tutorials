package com.baeldung.concurrent.locks;

import static junit.framework.TestCase.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.junit.Test;

public class SharedObjectWithLockManualTest {

    @Test
    public void whenLockAcquired_ThenLockedIsTrue() throws InterruptedException {
        Semaphore sem = new Semaphore(0);
        final SharedObjectWithLock object = new SharedObjectWithLock(sem);

        final int threadCount = 1;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        executeThreads(object, threadCount, service);
        sem.acquire();
        assertEquals(true, object.isLocked());
        sem.release();
        service.shutdown();
    }

    @Test
    public void whenLocked_ThenQueuedThread() {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        executeThreads(object, threadCount, service);

        assertEquals(object.hasQueuedThreads(), true);

        service.shutdown();

    }

    @Test
    public void whenGetCount_ThenCorrectCount() throws InterruptedException {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        executeThreads(object, threadCount, service);
        Thread.sleep(1000);
        assertEquals(object.getCounter(), 4);

        service.shutdown();

    }

    private void executeThreads(SharedObjectWithLock object, int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++) {
            service.execute(object::perform);
        }
    }

}

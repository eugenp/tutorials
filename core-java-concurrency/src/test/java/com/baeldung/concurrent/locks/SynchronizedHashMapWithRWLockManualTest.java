package com.baeldung.concurrent.locks;

import static junit.framework.TestCase.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;

public class SynchronizedHashMapWithRWLockManualTest {

    private Semaphore sem = new Semaphore(0);
    private SynchronizedHashMapWithRWLock object;

    @Before
    public void setup() {
        sem = new Semaphore(0);
        object = new SynchronizedHashMapWithRWLock(sem);
    }

    @Test
    public void whenWriting_ThenNoReading() throws InterruptedException {
        final int threadCount = 1;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        executeWriterThreads(threadCount, service);
        waitForAnyWriterStarted();
        assertEquals(object.isReadLockAvailable(), false);
        service.shutdown();
    }

    @Test
    public void whenReading_ThenMultipleReadingAllowed() {
        final int threadCount = 5;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        executeReaderThreads(threadCount, service);

        assertEquals(object.isReadLockAvailable(), true);

        service.shutdown();
    }

    private void waitForAnyWriterStarted() throws InterruptedException {
        sem.acquire();
    }

    private void executeWriterThreads(int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++) {
            service.execute(() -> {
                try {
                    object.put("key" + threadCount, "value" + threadCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void executeReaderThreads(int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++)
            service.execute(() -> object.get("key" + threadCount));
    }

}

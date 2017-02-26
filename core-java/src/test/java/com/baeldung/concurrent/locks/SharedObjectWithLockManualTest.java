package com.baeldung.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.TestCase.assertEquals;


public class SharedObjectWithLockManualTest {

    @Test
    public void whenLockAcquiredThenLockedIsTrue(){
        final SharedObjectWithLock object = new SharedObjectWithLock();

        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        executeThreads(object, threadCount, service);

        assertEquals(true, object.isLocked());


        service.shutdown();
    }


    @Test
    public void whenLockedThenQueuedThread(){
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        executeThreads(object, threadCount, service);

        assertEquals(object.hasQueuedThreads(), true);


        service.shutdown();

    }

    public void whenTryLockThenQueuedThread(){
        final SharedObjectWithLock object = new SharedObjectWithLock();

        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        executeThreads(object, threadCount, service);

        assertEquals(true, object.isLocked());


        service.shutdown();
    }

    @Test
    public void whenGetCountThenCorrectCount() throws InterruptedException {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        executeThreads(object, threadCount, service);
        Thread.sleep(1000);
        assertEquals(object.getCounter(), 4);


        service.shutdown();

    }

    private void executeThreads(SharedObjectWithLock object, int threadCount, ExecutorService service) {
        for(int i=0;i<threadCount;i++){
            service.execute(()-> {object.perform();});
        }
    }

}

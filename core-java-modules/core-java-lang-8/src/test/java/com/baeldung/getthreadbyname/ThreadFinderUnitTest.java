package com.baeldung.getthreadbyname;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ThreadFinderUnitTest {

    @Test
    public void givenThreadName_whenUsingGetAllStackTraces_thenThreadFound() throws InterruptedException {
        Thread testThread = new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "TestThread");
        testThread.start();

        Thread foundThread = ThreadFinder.getThreadByName("TestThread");
        assertNotNull(foundThread);
        assertEquals("TestThread", foundThread.getName());
        testThread.join(); // Ensure the thread finishes
    }

    @Test
    public void givenThreadName_whenUsingThreadGroup_thenThreadFound() throws InterruptedException {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        Thread testThread = new Thread(threadGroup, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "TestThread");
        testThread.start();

        Thread foundThread = ThreadFinder.getThreadByThreadGroupAndName(threadGroup, "TestThread");
        assertNotNull(foundThread);
        assertEquals("TestThread", foundThread.getName());
        testThread.join();
    }

}

package com.baeldung.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BlockingQueueUnitTest {

    @Test
    public void givenArrayBlockingQueue_whenAddedElements_thenReturnQueueRemainingCapacity() {
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        arrayBlockingQueue.add("TestString1");
        arrayBlockingQueue.add("TestString2");
        assertEquals(8, arrayBlockingQueue.remainingCapacity());
    }

    @Test
    public void givenLinkedBlockingQueue_whenAddedElements_thenReturnQueueRemainingCapacity() {
        BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        linkedBlockingQueue.add("TestString1");
        assertEquals(9, linkedBlockingQueue.remainingCapacity());
    }
}
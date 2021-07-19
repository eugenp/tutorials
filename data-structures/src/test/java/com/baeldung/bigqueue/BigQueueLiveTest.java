package com.baeldung.bigqueue;

import com.leansoft.bigqueue.BigQueueImpl;
import com.leansoft.bigqueue.IBigQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnit4.class)
public class BigQueueLiveTest {

    private IBigQueue bigQueue;

    @Before
    public void setup() throws IOException {
        String queueDir = System.getProperty("user.home");
        String queueName = "baeldung-queue";
        bigQueue = new BigQueueImpl(queueDir, queueName);
    }

    @After
    public void emptyQueue() throws IOException {
        bigQueue.removeAll();
        bigQueue.gc();
        bigQueue.close();
    }

    @Test
    public void whenAddingRecords_ThenTheSizeIsCorrect() throws IOException {
        for (int i = 1; i <= 100; i++) {
            bigQueue.enqueue(String.valueOf(i).getBytes());
        }

        assertEquals(100, bigQueue.size());
    }

    @Test
    public void whenAddingRecords_ThenTheyCanBeRetrieved() throws IOException {
        bigQueue.enqueue(String.valueOf("new_record").getBytes());

        String record = new String(bigQueue.dequeue());
        assertEquals("new_record", record);
    }

    @Test
    public void whenDequeueingRecords_ThenTheyAreConsumed() throws IOException {
        for (int i = 1; i <= 100; i++) {
            bigQueue.enqueue(String.valueOf(i).getBytes());
        }
        bigQueue.dequeue();

        assertEquals(99, bigQueue.size());
    }

    @Test
    public void whenPeekingRecords_ThenSizeDoesntChange() throws IOException {
        for (int i = 1; i <= 100; i++) {
            bigQueue.enqueue(String.valueOf(i).getBytes());
        }
        String firstRecord = new String(bigQueue.peek());

        assertEquals("1", firstRecord);
        assertEquals(100, bigQueue.size());
    }

    @Test
    public void whenEmptyingTheQueue_ThenItSizeIs0() throws IOException {
        for (int i = 1; i <= 100; i++) {
            bigQueue.enqueue(String.valueOf(i).getBytes());
        }
        bigQueue.removeAll();

        assertEquals(0, bigQueue.size());
    }

}

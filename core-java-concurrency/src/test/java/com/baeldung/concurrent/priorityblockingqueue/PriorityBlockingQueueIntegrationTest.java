package com.baeldung.concurrent.priorityblockingqueue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

public class PriorityBlockingQueueIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(PriorityBlockingQueueIntegrationTest.class);


    @Test
    public void givenUnorderedValues_whenPolling_thenShouldOrderQueue() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        ArrayList<Integer> polledElements = new ArrayList<>();

        queue.add(1);
        queue.add(5);
        queue.add(2);
        queue.add(3);
        queue.add(4);

        queue.drainTo(polledElements);

        assertThat(polledElements).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    public void whenPollingEmptyQueue_thenShouldBlockThread() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        final Thread thread = new Thread(() -> {
            LOG.debug("Polling...");
            while (true) {
                try {
                    Integer poll = queue.take();
                    LOG.debug("Polled: " + poll);
                } catch (InterruptedException ignored) {
                }
            }
        });
        thread.start();

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        LOG.debug("Adding to queue");

        queue.addAll(newArrayList(1, 5, 6, 1, 2, 6, 7));
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
    }
}

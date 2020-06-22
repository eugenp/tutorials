package com.baeldung.concurrent.queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder
public class TestConcurrentLinkedQueue {

    @Test
    public void givenThereIsExistingCollection_WhenAddedIntoQueue_ThenShouldContainElements() {
        Collection<Integer> elements = Arrays.asList(1, 2, 3, 4, 5);
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>(elements);
        assertThat(concurrentLinkedQueue).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    public void givenQueueIsEmpty_WhenAccessingTheQueue_ThenQueueReturnsNull() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        executorService.submit(() -> assertNull("Retrieve object is null", concurrentLinkedQueue.poll()));
        TimeUnit.SECONDS.sleep(1);
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    @Test
    public void givenProducerOffersElementInQueue_WhenConsumerPollsQueue_ThenItRetrievesElement() throws Exception {
        int element = 1;

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        Runnable offerTask = () -> concurrentLinkedQueue.offer(element);

        Callable<Integer> pollTask = () -> {
            while (concurrentLinkedQueue.peek() != null) {
                return concurrentLinkedQueue.poll()
                    .intValue();
            }
            return null;
        };

        executorService.submit(offerTask);
        TimeUnit.SECONDS.sleep(1);

        Future<Integer> returnedElement = executorService.submit(pollTask);
        assertThat(returnedElement.get()
            .intValue(), is(equalTo(element)));
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }
}

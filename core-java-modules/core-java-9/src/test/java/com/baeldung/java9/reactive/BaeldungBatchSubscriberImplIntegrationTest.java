package com.baeldung.java9.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;

public class BaeldungBatchSubscriberImplIntegrationTest {

    private static final int ITEM_SIZE = 10;
    private SubmissionPublisher<String> publisher;
    private BaeldungBatchSubscriberImpl<String> subscriber;

    @Before
    public void initialize() {
        this.publisher = new SubmissionPublisher<String>(ForkJoinPool.commonPool(), 6);
        this.subscriber = new BaeldungBatchSubscriberImpl<String>();
        publisher.subscribe(subscriber);
    }

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {

    };

    @Test
    public void testReactiveStreamCount() {
        IntStream.range(0, ITEM_SIZE)
            .forEach(item -> publisher.submit(item + ""));
        publisher.close();

        do {
            // wait for subscribers to complete all processing.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (!subscriber.isCompleted());

        int count = subscriber.getCounter();

        assertEquals(ITEM_SIZE, count);
    }

    @Test
    public void testReactiveStreamTime() {
        IntStream.range(0, ITEM_SIZE)
            .forEach(item -> publisher.submit(item + ""));
        publisher.close();

        do {
            // wait for subscribers to complete all processing.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (!subscriber.isCompleted());

        // The runtime in seconds should be equal to the number of items in each batch.
        assertTrue(stopwatch.runtime(TimeUnit.SECONDS) >= (ITEM_SIZE / subscriber.BUFFER_SIZE));
    }

}

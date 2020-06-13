package com.baeldung.jctools;

import org.assertj.core.api.Assertions;
import org.jctools.queues.SpscArrayQueue;
import org.jctools.queues.SpscChunkedArrayQueue;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class JCToolsUnitTest {

    @Test
    public void givenMultipleProducers_whenSpscQueueUsed_thenNoWarningOccurs() throws InterruptedException {
        SpscArrayQueue<Integer> queue = new SpscArrayQueue<Integer>(2);

        Thread producer1 = new Thread(() -> {
            queue.offer(1);
        });
        producer1.start();
        producer1.join();

        Thread producer2 = new Thread(() -> {
            queue.offer(2);
        });
        producer2.start();
        producer2.join();

        Set<Integer> fromQueue = new HashSet<>();
        Thread consumer = new Thread(() -> queue.drain(fromQueue::add));
        consumer.start();
        consumer.join();

        assertThat(fromQueue).containsOnly(1, 2);
    }

    @Test
    public void whenQueueIsFull_thenNoMoreElementsCanBeAdded() throws InterruptedException {
        SpscChunkedArrayQueue<Integer> queue = new SpscChunkedArrayQueue<>(8, 16);
        Assertions.assertThat(queue.capacity()).isEqualTo(16);

        CountDownLatch startConsuming = new CountDownLatch(1);
        CountDownLatch awakeProducer = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();
        Thread producer = new Thread(() -> {
            IntStream.range(0, queue.capacity()).forEach(i -> {
                Assertions.assertThat(queue.offer(i)).isTrue();
            });
            Assertions.assertThat(queue.offer(queue.capacity())).isFalse();
            startConsuming.countDown();
            try {
                awakeProducer.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Assertions.assertThat(queue.offer(queue.capacity())).isTrue();
        });
        producer.setUncaughtExceptionHandler((t, e) -> {
            error.set(e);
            startConsuming.countDown();
        });
        producer.start();

        startConsuming.await();

        if (error.get() != null) {
            fail("Producer's assertion failed", error.get());
        }

        Set<Integer> fromQueue = new HashSet<>();
        queue.drain(fromQueue::add);
        awakeProducer.countDown();
        producer.join();
        queue.drain(fromQueue::add);

        assertThat(fromQueue).containsAll(IntStream.range(0, 17).boxed().collect(Collectors.toSet()));
    }
}

package com.baeldung.circularbuffer;

import static org.junit.Assert.assertArrayEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.jupiter.api.Test;

public class ProducerConsumerLiveTest {

    private final String[] shapes = { "Circle", "Triangle", "Rectangle", "Square", "Rhombus", "Trapezoid", "Pentagon", "Pentagram", "Hexagon", "Hexagram" };

    @Test
    public void givenACircularBuffer_whenInterleavingProducerConsumer_thenElementsMatch() throws Exception {
        CircularBuffer<String> buffer = new CircularBuffer<String>(shapes.length);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Producer<String>(buffer, shapes));
        Future<String[]> consumed = executorService.submit(new Consumer<String>(buffer, shapes.length));

        String[] shapesConsumed = consumed.get(5L, TimeUnit.SECONDS);
        assertArrayEquals(shapes, shapesConsumed);
    }

    static class Producer<T> implements Runnable {

        private CircularBuffer<T> buffer;
        private T[] items;

        public Producer(CircularBuffer<T> buffer, T[] items) {
            this.buffer = buffer;
            this.items = items;
        }

        @Override
        public void run() {

            for (int i = 0; i < items.length;) {
                if (buffer.offer(items[i])) {
                    System.out.println("Produced: " + items[i]);
                    i++;
                    LockSupport.parkNanos(5);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    static class Consumer<T> implements Callable<T[]> {

        private CircularBuffer<T> buffer;
        private int expectedCount;

        public Consumer(CircularBuffer<T> buffer, int expectedCount) {
            this.buffer = buffer;
            this.expectedCount = expectedCount;
        }

        @Override
        public T[] call() throws Exception {
            T[] items = (T[]) new Object[expectedCount];
            for (int i = 0; i < items.length;) {
                T item = buffer.poll();
                if (item != null) {
                    items[i++] = item;

                    LockSupport.parkNanos(5);
                    System.out.println("Consumed: " + item);
                }
            }
            return items;
        }
    }
}

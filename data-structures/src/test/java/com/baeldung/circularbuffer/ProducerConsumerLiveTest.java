package com.baeldung.circularbuffer;

import static org.junit.Assert.assertArrayEquals;

import java.util.concurrent.locks.LockSupport;

import org.junit.jupiter.api.Test;

public class ProducerConsumerLiveTest {

    private final String[] shapes = { "Circle", "Triangle", "Rectangle", "Square", "Rhombus", "Trapezoid", "Pentagon", "Pentagram", "Hexagon", "Hexagram" };

    @Test
    public void givenACircularBuffer_whenInterleavingProducerConsumer_thenElementsMatch() throws InterruptedException {
        CircularBuffer<String> buffer = new CircularBuffer<String>(shapes.length);
        String[] consumedShapes = new String[shapes.length];

        Thread producer = new Thread(new Producer(shapes, buffer));
        Thread consumer = new Thread(new Consumer(consumedShapes, buffer));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertArrayEquals(shapes, consumedShapes);
    }

    static class Producer implements Runnable {

        private String[] producerShapes;
        private CircularBuffer<String> buffer;

        public Producer(String[] producerShapes, CircularBuffer<String> buffer) {
            this.producerShapes = producerShapes;
            this.buffer = buffer;
        }

        @Override
        public void run() {

            for (int i = 0; i < producerShapes.length;) {
                if (buffer.offer(producerShapes[i])) {
                    System.out.println("Produced: " + producerShapes[i]);
                    i++;
                    LockSupport.parkNanos(5);
                }
            }
        }
    }

    static class Consumer implements Runnable {

        private CircularBuffer<String> buffer;
        private String[] consumedShapes;

        public Consumer(String[] consumedShapes, CircularBuffer<String> buffer) {
            this.consumedShapes = consumedShapes;
            this.buffer = buffer;
        }

        @Override
        public void run() {

            for (int i = 0; i < consumedShapes.length;) {
                String shape = buffer.poll();
                if (shape != null) {
                    consumedShapes[i++] = shape;

                    LockSupport.parkNanos(5);
                    System.out.println("Consumed: " + shape);
                }
            }
        }
    }
}

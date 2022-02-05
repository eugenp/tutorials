package com.baeldung.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class DataQueue {
    private final Queue<Message> queue;
    private final int maxSize;
    private final Object FULL_QUEUE = new Object();
    private final Object EMPTY_QUEUE = new Object();

    DataQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new LinkedList<>();
    }

    public boolean isFull() {
        return queue.size() == maxSize;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void waitOnFull() throws InterruptedException {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.wait();
        }
    }

    public void waitOnEmpty() throws InterruptedException {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.wait();
        }
    }

    public void notifyForFull() {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.notify();
        }
    }

    public void notifyForEmpty() {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.notify();
        }
    }

    private void notifyAllForFull() {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.notifyAll();
        }
    }

    private void notifyAllForEmpty() {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.notifyAll();
        }
    }

    public void stop() {
        notifyAllForFull();
        notifyAllForEmpty();
    }

    public void add(Message message) {
        synchronized (queue) {
            queue.add(message);
        }
    }

    public Message remove() {
        synchronized (queue) {
            return queue.poll();
        }
    }
}

package com.baeldung.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class DataQueue {

    private final Queue<Message> queue = new LinkedList<>();
    private final int maxSize;

    DataQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void add(Message message)
            throws InterruptedException {

        while (queue.size() == maxSize) {
            wait();
        }

        queue.add(message);

        notifyAll();
    }

    public synchronized Message poll()
            throws InterruptedException {

        while (queue.isEmpty()) {
            wait();
        }

        Message message = queue.poll();

        notifyAll();

        return message;
    }

    public synchronized Integer getSize() {
        return queue.size();
    }
}
package com.baeldung.threadsafelifo;

import java.util.ArrayDeque;

/**
 * Deque Based Stack implementation.
 */
public class DequeBasedSynchronizedStack<T> {

    // Internal Deque which gets decorated for synchronization.
    private ArrayDeque<T> dequeStore = new ArrayDeque<>();

    public DequeBasedSynchronizedStack(int initialCapacity) {
        this.dequeStore = new ArrayDeque<>(initialCapacity);
    }

    public DequeBasedSynchronizedStack() {

    }

    public synchronized T pop() {
        return this.dequeStore.pop();
    }

    public synchronized void push(T element) {
        this.dequeStore.push(element);
    }

    public synchronized T peek() {
        return this.dequeStore.peek();
    }

    public synchronized int size() {
        return this.dequeStore.size();
    }
}

package com.baeldung.circularbuffer;

public class CircularBuffer<E> {

    private int capacity, writeIndex, readIndex;
    private E[] data;

    @SuppressWarnings("unchecked")
    public CircularBuffer(int capacity) {

        this.capacity = capacity;
        this.data = (E[]) new Object[capacity];

        this.readIndex = 0;
        this.writeIndex = -1;
    }

    public boolean offer(E element) {

        if (!isFull()) {

            writeIndex += 1;
            int nextWriteIndex = writeIndex % capacity;
            data[nextWriteIndex] = element;

            return true;
        }

        return false;
    }

    public E poll() {

        if (!isEmpty()) {

            int nextReadIndex = readIndex % capacity;
            readIndex += 1;

            return data[nextReadIndex];
        }

        return null;
    }

    public E peek() {

        if (!isEmpty()) {

            return data[readIndex % capacity];
        }

        return null;
    }

    public int capacity() {
        return capacity;
    }

    public int size() {

        return (writeIndex - readIndex) + 1;
    }

    public boolean isEmpty() {

        return writeIndex < readIndex;
    }

    public boolean isFull() {

        return size() >= capacity;
    }
}

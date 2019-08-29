package com.baeldung.typeerasure;

import java.util.Arrays;

public class BoundStack<E extends Comparable<E>> {

    private E[] stackContent;
    private int total;

    public BoundStack(int capacity) {
        this.stackContent = (E[]) new Object[capacity];
    }

    public void push(E data) {
        if (total == stackContent.length) {
            resize(2 * stackContent.length);
        }
        stackContent[total++] = data;
    }

    public E pop() {
        if (!isEmpty()) {
            E datum = stackContent[total];
            stackContent[total--] = null;
            return datum;
        }
        return null;
    }

    private void resize(int capacity) {
        Arrays.copyOf(stackContent, capacity);
    }

    public boolean isEmpty() {
        return total == 0;
    }
}

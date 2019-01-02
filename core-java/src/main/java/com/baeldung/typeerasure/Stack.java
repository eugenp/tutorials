package com.baeldung.typeerasure;

import java.util.Arrays;

/**
 * @author zn.wang
 */
public class Stack<E> {

    private E[] stackContent;
    private int total;

    public Stack(int capacity) {
        this.stackContent = (E[]) new Object[capacity];
    }

    public synchronized void push(E data) {
        System.out.println("In base stack push#");

        if (total == stackContent.length) {
            resize(2 * stackContent.length);
        }
        stackContent[total++] = data;
    }

    public synchronized E pop() {
        if (!isEmpty()) {
            E datum = stackContent[total];
            stackContent[total--] = null;
            return datum;
        }
        return null;
    }

    private synchronized void resize(int capacity) {
        Arrays.copyOf(stackContent, capacity);
    }

    public synchronized boolean isEmpty() {
        return total == 0;
    }

}

package com.baeldung.genericarrays;

import java.lang.reflect.Array;

public class MyStack<E> {
    private E[] elements;
    private int size = 0;

    public MyStack(Class<E> clazz, int capacity) {
        elements = (E[]) Array.newInstance(clazz, capacity);
    }

    public void push(E item) {
        if (size == elements.length) {
            throw new MyStackFullException();
        }
        elements[size++] = item;
    }

    public E pop() {
        if (size == 0) {
            throw new MyStackEmptyException();
        }
        return elements[--size];
    }

    public E[] getAllElements() {
        return elements;
    }
}

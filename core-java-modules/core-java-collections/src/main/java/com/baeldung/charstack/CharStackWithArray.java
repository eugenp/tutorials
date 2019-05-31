package com.baeldung.charstack;

public class CharStackWithArray {

    private char[] elements;
    private int size;

    public CharStackWithArray() {
        size = 0;
        elements = new char[4];
    }

    public int size() {
        return size;
    }

    public char peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return elements[size - 1];
    }

    public char pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }

        return elements[--size];
    }

    public void push(char item) {
        ensureCapacity(size + 1);
        elements[size] = item;
        size++;
    }

    private void ensureCapacity(int newSize) {
        char newBiggerArray[];

        if (elements.length < newSize) {
            newBiggerArray = new char[elements.length * 2];
            System.arraycopy(elements, 0, newBiggerArray, 0, size);
            elements = newBiggerArray;
        }
    }

}

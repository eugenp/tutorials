package com.baeldung.genericarrays;

public class GenericListWithObject<T> {
    private Object[] elements;
    private int size = 0;
    private static final int CAPACITY = 5;

    public GenericListWithObject() {
        this.elements = new Object[CAPACITY];
    }

    public void add(T element) {
        if (size == elements.length) {
            throw new ListFullException();
        }
        this.elements[size++] = element;
    }

    public T get(int index) {
        if (index > (size - 1)) {
            throw new ListElementDoesNotExistException();
        }
        return (T) elements[index];
    }
}

package com.baeldung.genericarrays;

public class GenericList<T> {
    private T[] elements;
    private int size = 0;
    private static final int CAPACITY = 5;

@SuppressWarnings("unchecked")
public GenericList() {
    this.elements = (T[]) new Object[CAPACITY];
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
        return elements[index];
    }
}

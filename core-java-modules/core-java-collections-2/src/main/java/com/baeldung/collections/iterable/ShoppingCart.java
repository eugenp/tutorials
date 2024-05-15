package com.baeldung.collections.iterable;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ShoppingCart<E> implements Iterable<E> {

    private E[] elementData;
    private int size;

    public ShoppingCart() {
        this.elementData = (E[]) new Object[]{};
    }

    public void add(E element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;
    }

    private void ensureCapacity(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    public Iterator<E> iterator() {
        return new ShoppingCartIterator();
    }

    public class ShoppingCartIterator implements Iterator<E> {
        int cursor;
        int lastReturned = -1;

        public boolean hasNext() {
            return cursor != size;
        }

        public E next() {
            return getNextElement();
        }

        private E getNextElement() {
            int current = cursor;
            exist(current);

            E[] elements = ShoppingCart.this.elementData;
            validate(elements, current);

            cursor = current + 1;
            lastReturned = current;
            return elements[lastReturned];
        }

        private void exist(int current) {
            if (current >= size) {
                throw new NoSuchElementException();
            }
        }

        private void validate(E[] elements, int current) {
            if (current >= elements.length) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

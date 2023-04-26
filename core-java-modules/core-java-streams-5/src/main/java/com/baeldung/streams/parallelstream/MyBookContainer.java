package com.baeldung.streams.parallelstream;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MyBookContainer<T> implements Collection<T> {
    private static final long serialVersionUID = 1L;
    private T[] elements;

    public MyBookContainer(T[] elements) {
        this.elements = elements;
    }

    @Override
    public Spliterator<T> spliterator() {
        return new BookSpliterator(elements, 0);
    }

    @Override
    public Stream<T> parallelStream() {
        return StreamSupport.stream(spliterator(), false);
    }

    // standard overridden methods of Collection Interface

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public boolean isEmpty() {
        return elements.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
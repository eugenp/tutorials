package com.baeldung.adapter;

import java.util.Enumeration;
import java.util.Iterator;

public class IteratorAdapter<E> implements Iterator<E> {

    private Enumeration<E> enumeration;

    public IteratorAdapter(Enumeration<E> enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public E next() {
        return enumeration.nextElement();
    }

}

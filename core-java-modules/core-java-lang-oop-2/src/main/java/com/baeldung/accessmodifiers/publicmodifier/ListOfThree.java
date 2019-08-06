package com.baeldung.accessmodifiers.publicmodifier;

import java.util.AbstractList;

public class ListOfThree<E> extends AbstractList<E> {

    private static final int MAX_LENGTH = 3;
    private int size;
    private Object[] elements = new Object[MAX_LENGTH];
    
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {        
        return (E)elements[index];
    }

    @Override
    public boolean add(E e) {
        
        elements[size] = e;
        size++;
        
        return true;
    }
    
    @Override
    public int size() {
        return size;
    }

}

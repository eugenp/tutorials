package com.baeldung.publicmodifier;

import java.util.AbstractList;
import java.util.Arrays;

public class ListOfThree<E> extends AbstractList<E> {

    private static final int LENGTH = 3;
    private Object[] elements;
    
    public ListOfThree(E[] data) {
        if(data == null 
            || data.length != LENGTH)
            throw new IllegalArgumentException();
        
        this.elements = Arrays.copyOf(data, data.length); //shallow copy
        
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {      
        return (E)elements[index];
    }
    
    @Override
    public int size() {
        return LENGTH;
    }

}

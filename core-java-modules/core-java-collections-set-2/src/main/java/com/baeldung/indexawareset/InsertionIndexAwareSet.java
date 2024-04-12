package com.baeldung.indexawareset;

import java.util.LinkedHashSet;

public class InsertionIndexAwareSet<E> extends LinkedHashSet<E> {
    public InsertionIndexAwareSet() {
        super();
    }

    public int getIndexOf(E element) {
        int index = 0;
        for (E current : this) {
            if (current.equals(element)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}

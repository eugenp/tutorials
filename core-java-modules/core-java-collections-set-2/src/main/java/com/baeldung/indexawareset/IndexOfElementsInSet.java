package com.baeldung.indexawareset;

import java.util.Iterator;
import java.util.Set;

public class IndexOfElementsInSet<E> {
    public int getIndexUsingIterator(Set<E> set, E element) {
        Iterator<E> iterator = set.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (element.equals(iterator.next())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int getIndexUsingForEach(Set<E> set, E element) {
        int index = 0;
        for (E current : set) {
            if (element.equals(current)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}

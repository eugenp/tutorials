package com.baeldung.linkedhashsetindexof;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class LinkedHashSetIndexLookup {

    public static <E> int getIndex(LinkedHashSet<E> set, E element) {
        int index = 0;
        for (E current : set) {
            if (current.equals(element)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static <E> int getIndexUsingIterator(LinkedHashSet<E> set, E element) {
        Iterator<E> iterator = set.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().equals(element)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}


package com.baeldung.linkedhashsetindexof;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LinkedHashSetWithConversion {

    public static <E> List<E> convertToList(Set<E> set) {
        return new ArrayList<>(set);
    }

    public static <E> int getIndexByConversion(Set<E> set, E element) {
        List<E> list = new ArrayList<>(set);
        return list.indexOf(element);
    }

    public static <E> E getElementByIndex(Set<E> set, int index) {
        List<E> list = new ArrayList<>(set);
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + list.size());
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] convertToArray(Set<E> set, Class<E> clazz) {
        return set.toArray((E[]) java.lang.reflect.Array.newInstance(clazz, set.size()));
    }

    public static <E> int getIndexByArray(Set<E> set, E element, Class<E> clazz) {
        E[] array = convertToArray(set, clazz);
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
}


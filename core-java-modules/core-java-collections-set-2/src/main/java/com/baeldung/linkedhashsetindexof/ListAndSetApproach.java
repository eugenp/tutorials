package com.baeldung.linkedhashsetindexof;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListAndSetApproach<E> {
    private final List<E> list;
    private final Set<E> set;

    public ListAndSetApproach() {
        this.list = new ArrayList<>();
        this.set = new HashSet<>();
    }

    public boolean add(E element) {
        if (set.add(element)) {
            list.add(element);
            return true;
        }
        return false;
    }

    public boolean remove(E element) {
        if (set.remove(element)) {
            list.remove(element);
            return true;
        }
        return false;
    }

    public int indexOf(E element) {
        return list.indexOf(element);
    }

    public E get(int index) {
        return list.get(index);
    }

    public boolean contains(E element) {
        return set.contains(element);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        list.clear();
        set.clear();
    }
}


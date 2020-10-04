package com.baeldung.genericarrays;

import java.util.ArrayList;
import java.util.List;

public class CollectionsList<T> {
    private List<T> elements;

    public CollectionsList() {
        this.elements = new ArrayList<>();
    }

    public void add(T element) {
        elements.add(element);
    }

    public void addAll(List<? extends T> elements) {
        for (T element : elements) {
            add(element);
        }
    }

    public T get(int index) {
        return elements.get(index);
    }
}

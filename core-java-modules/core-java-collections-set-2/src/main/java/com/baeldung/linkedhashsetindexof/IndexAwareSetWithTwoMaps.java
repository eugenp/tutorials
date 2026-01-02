package com.baeldung.linkedhashsetindexof;

import java.util.HashMap;
import java.util.Map;

public class IndexAwareSetWithTwoMaps<E> {
    private final Map<E, Integer> elementToIndex;
    private final Map<Integer, E> indexToElement;
    private int nextIndex;

    public IndexAwareSetWithTwoMaps() {
        this.elementToIndex = new HashMap<>();
        this.indexToElement = new HashMap<>();
        this.nextIndex = 0;
    }

    public boolean add(E element) {
        if (elementToIndex.containsKey(element)) {
            return false;
        }
        elementToIndex.put(element, nextIndex);
        indexToElement.put(nextIndex, element);
        nextIndex++;
        return true;
    }

    public boolean remove(E element) {
        Integer index = elementToIndex.get(element);
        if (index == null) {
            return false;
        }
        
        elementToIndex.remove(element);
        indexToElement.remove(index);
        
        for (int i = index + 1; i < nextIndex; i++) {
            E elementAtI = indexToElement.get(i);
            if (elementAtI != null) {
                indexToElement.remove(i);
                elementToIndex.put(elementAtI, i - 1);
                indexToElement.put(i - 1, elementAtI);
            }
        }
        
        nextIndex--;
        return true;
    }

    public int indexOf(E element) {
        return elementToIndex.getOrDefault(element, -1);
    }

    public E get(int index) {
        if (index < 0 || index >= nextIndex) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nextIndex);
        }
        return indexToElement.get(index);
    }
}


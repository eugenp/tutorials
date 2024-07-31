package com.baeldung.algorithms.minheapmerge;

public class HeapNode {

    int element;
    int arrayIndex;
    int nextElementIndex = 1;

    public HeapNode(int element, int arrayIndex) {
        this.element = element;
        this.arrayIndex = arrayIndex;
    }
}

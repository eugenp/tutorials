package com.baeldung.algorithms.linkedlist;

public class CycleDetectionResult<T> {
    boolean cycleExists;
    Node<T> node;

    public CycleDetectionResult(boolean cycleExists, Node<T> node) {
        super();
        this.cycleExists = cycleExists;
        this.node = node;
    }
}

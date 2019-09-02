package com.baeldung.algorithms.breadthfirstsearch;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node<T> {

    private T value;
    private Set<Node<T>> neighbours;

    public Node(T value) {
        this.value = value;
        this.neighbours = new HashSet<>();
    }

    public T getValue() {
        return value;
    }

    public Set<Node<T>> getNeighbours() {
        return Collections.unmodifiableSet(neighbours);
    }

    public void connect(Node<T> node) {
        if (this == node) throw new IllegalArgumentException("Can't connect node to itself");
        this.neighbours.add(node);
        node.neighbours.add(this);
    }

}

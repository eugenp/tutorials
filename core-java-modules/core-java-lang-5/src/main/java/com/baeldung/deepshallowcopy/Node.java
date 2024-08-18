package com.baeldung.deepshallowcopy;

import java.io.Serializable;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

class Node<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    T value;

    Node<T> next;

    public Node() {
    }

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    public Node<T> copy() {
        Node<T> c = new Node<T>(value, next);

        c.replaceNext(this, c);

        return c;
    }

    void replaceNext(Node<T> from, Node<T> to) {
        Set<Node<T>> seen = Collections.newSetFromMap(new IdentityHashMap<>());

        replaceNextDfs(seen, from, to);
    }

    void replaceNextDfs(Set<Node<T>> seen, Node<T> from, Node<T> to) {
        if (!seen.add(this)) {
            return;
        }

        Node<T> n = next;

        if (n == null) {
            return;
        }

        if (n == from) {
            next = to;
        }

        n.replaceNextDfs(seen, from, to);
    }

}
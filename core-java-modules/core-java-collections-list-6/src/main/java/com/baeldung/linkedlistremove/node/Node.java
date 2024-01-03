package com.baeldung.linkedlistremove.node;

public interface Node<T> {

    T getElement();
    boolean hasNext();

    Node<T> getNext();

    void setNext(Node<T> next);
}

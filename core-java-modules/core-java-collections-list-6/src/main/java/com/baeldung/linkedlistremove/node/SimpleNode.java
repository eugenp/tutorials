package com.baeldung.linkedlistremove.node;

public class SimpleNode<T> implements Node<T> {

    private T element;
    private Node<T> next;

    public SimpleNode(T element) {
        this.element = element;
    }

    @Override
    public T getElement() {
        return element;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Node<T> getNext() {
        return next;
    }

    @Override
    public void setNext(Node<T> next) {
        this.next = next;
    }
}

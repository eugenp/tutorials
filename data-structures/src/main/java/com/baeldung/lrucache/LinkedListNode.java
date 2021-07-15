package com.baeldung.lrucache;

public interface LinkedListNode<V> {
    boolean hasElement();

    boolean isEmpty();

    V getElement() throws NullPointerException;

    void detach();

    DoublyLinkedList<V> getListReference();

    LinkedListNode<V> setPrev(LinkedListNode<V> prev);

    LinkedListNode<V> setNext(LinkedListNode<V> next);

    LinkedListNode<V> getPrev();

    LinkedListNode<V> getNext();

    LinkedListNode<V> search(V value);
}

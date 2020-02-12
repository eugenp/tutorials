package com.baeldung.algorithms.twopointertechnique;

public class MyNode<E> {
    MyNode<E> next;
    E data;

    public MyNode(E value) {
        data = value;
        next = null;
    }

    public MyNode(E value, MyNode<E> n) {
        data = value;
        next = n;
    }

    public void setNext(MyNode<E> n) {
        next = n;
    }
}

package com.baeldung.algorithms.middleelementlookup;

public class Node {
    private Node next;
    private String data;

    public Node(String data) {
        this.data = data;
    }

    public String data() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean hasNext() {
        return next != null;
    }

    public Node next() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public String toString() {
        return this.data;
    }
}

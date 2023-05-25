package com.baeldung.algorithms.linkedlist;

public class Node<T> {
    T data;
    Node<T> next;

    public static <T> Node<T> createNewNode(T data, Node<T> next) {
        Node<T> node = new Node<T>();
        node.data = data;
        node.next = next;
        return node;
    }

    public static <T> void traverseList(Node<T> root) {
        if (root == null) {
            return;
        }

        Node<T> node = root;
        while (node != null) {
            System.out.println(node.data);
            node = node.next;
        }
    }

    public static <T> Node<T> getTail(Node<T> root) {
        if (root == null) {
            return null;
        }

        Node<T> node = root;
        while (node.next != null) {
            node = node.next;
        }
        return node;
    }

}
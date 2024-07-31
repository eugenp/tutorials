package com.baeldung.linkedlistremove;

import java.util.Objects;

public class SinglyLinkedList<S> {

    private int size;
    private Node<S> head = null;
    private Node<S> tail = null;

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void add(S element) {
        Node<S> newTail = new Node<>(element);
        if (head == null) {
            tail = newTail;
            head = tail;
        } else {
            tail.next = newTail;
            tail = newTail;
        }
        ++size;
    }

    public void remove(S element) {
        if (isEmpty()) {
            return;
        }

        Node<S> previous = null;
        Node<S> current = head;
        while (current != null) {
            if (Objects.equals(element, current.element)) {
                Node<S> next = current.next;
                if (isFistNode(current)) {
                    head = next;
                } else if (isLastNode(current)) {
                    previous.next = null;
                } else {
                    Node<S> next1 = current.next;
                    previous.next = next1;
                }
                --size;
                break;
            }
            previous = current;
            current = current.next;
        }
    }

    public void removeLast() {
        if (isEmpty()) {
            return;
        } else if (size() == 1) {
            tail = null;
            head = null;
        } else {
            Node<S> secondToLast = null;
            Node<S> last = head;
            while (last.next != null) {
                secondToLast = last;
                last = last.next;
            }
            secondToLast.next = null;
        }
        --size;
    }

    public boolean contains(S element) {
        if (isEmpty()) {
            return false;
        }

        Node<S> current = head;
        while (current != null) {
            if (Objects.equals(element, current.element))
                return true;
            current = current.next;
        }
        return false;
    }

    private boolean isLastNode(Node<S> node) {
        return tail == node;
    }

    private boolean isFistNode(Node<S> node) {
        return head == node;
    }


    public static class Node<T>  {
        private T element;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

}

package com.baeldung.linkedlistremove;

public class SinglyLinkedList<S> {

    private int size;
    private Node<S> head = null;
    private Node<S> tail = null;

    public boolean isEmpty() {
        return head == null;
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

    public int size() {
        return size;
    }

    public void remove(S element) {
        if (isEmpty())
            return;

        if (element == null)
            return;

        Node<S> previous = null;
        Node<S> current = head;
        while (current != null) {
            if (element.equals(current.element)) {
                Node<S> next = current.next;
                if (isFistNode(current)) {
                    head = next;
                } else if (isLastNode(current)) {
                    previous.next = null;
                } else {
                    previous.next = current.next;
                }
                --size;
                break;
            }
            previous = current;
            current = current.next;
        }
    }

    private boolean isLastNode(Node<S> node) {
        return tail == node;
    }

    private boolean isFistNode(Node<S> node) {
        return head == node;
    }

    public boolean contains(S element) {
        if (isEmpty())
            return false;

        if (element == null)
            return false;

        Node<S> current = head;
        while (current != null) {
            if (element.equals(current.element))
                return true;
            current = current.next;
        }
        return false;
    }

    public void removeLast() {
        if (isEmpty())
            return;
        if (size() == 1) {
            tail = null;
            head = null;

        } else {
            Node<S> secondToLast = null;
            Node<S> last = head;
            while (last.hasNext()) {
                secondToLast = last;
                last = last.next;
            }
            secondToLast.next = null;
        }
        --size;
    }


    private static class Node<S> {

        private S element;
        private Node<S> next;

        public Node(S element) {
            this.element = element;
        }

        public boolean hasNext() {
            return next != null;
        }
    }
}

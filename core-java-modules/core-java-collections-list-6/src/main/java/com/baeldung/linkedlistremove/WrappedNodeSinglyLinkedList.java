package com.baeldung.linkedlistremove;

import com.baeldung.linkedlistremove.node.Node;
import com.baeldung.linkedlistremove.node.SimpleNode;

public class WrappedNodeSinglyLinkedList<S> {

    private int size;
    private Node<S> head = null;
    private Node<S> tail = null;

    public boolean isEmpty() {
        return head == null;
    }

    public void add(S element) {
        Node<S> newTail = new SimpleNode<>(element);
        if (head == null) {
            tail = newTail;
            head = tail;
        } else {
            tail.setNext(newTail);
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
            if (element.equals(current.getElement())) {
                Node<S> next = current.getNext();
                if (isFistNode(current)) {
                    head = next;
                } else if (isLastNode(current)) {
                    previous.setNext(null);
                } else {
                    previous.setNext(current.getNext());
                }
                --size;
                break;
            }
            previous = current;
            current = current.getNext();
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
            if (element.equals(current.getElement()))
                return true;
            current = current.getNext();
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
                last = last.getNext();
            }
            secondToLast.setNext(null);
        }
        --size;
    }

}

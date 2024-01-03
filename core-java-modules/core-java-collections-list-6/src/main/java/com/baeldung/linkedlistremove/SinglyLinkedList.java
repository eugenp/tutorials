package com.baeldung.linkedlistremove;

import com.baeldung.linkedlistremove.node.Node;
import com.baeldung.linkedlistremove.node.SimpleNode;
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

    public void remove(S element) {
        if (isEmpty())
            return;

        Node<S> previous = null;
        Node<S> current = head;
        while (current != null) {
            if (Objects.equals(element, current.getElement())) {
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

    public boolean contains(S element) {
        if (isEmpty())
            return false;

        Node<S> current = head;
        while (current != null) {
            if (Objects.equals(element, current.getElement()))
                return true;
            current = current.getNext();
        }
        return false;
    }

    private boolean isLastNode(Node<S> node) {
        return tail == node;
    }

    private boolean isFistNode(Node<S> node) {
        return head == node;
    }

}

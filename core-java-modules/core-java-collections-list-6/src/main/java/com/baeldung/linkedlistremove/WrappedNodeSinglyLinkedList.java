package com.baeldung.linkedlistremove;

public class WrappedNodeSinglyLinkedList<S> {

    private int size;
    private WrappedNode<S> head = null;
    private WrappedNode<S> tail = null;

    public boolean isEmpty() {
        return head == null;
    }

    public void add(S element) {
        WrappedNode<S> newTail = new WrappedNode<>(element);
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

        WrappedNode<S> previous = null;
        WrappedNode<S> current = head;
        while (current != null) {
            if (element.equals(current.element)) {
                WrappedNode<S> next = current.next;
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

    private boolean isLastNode(WrappedNode<S> wrappedNode) {
        return tail == wrappedNode;
    }

    private boolean isFistNode(WrappedNode<S> wrappedNode) {
        return head == wrappedNode;
    }

    public boolean contains(S element) {
        if (isEmpty())
            return false;

        if (element == null)
            return false;

        WrappedNode<S> current = head;
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
            WrappedNode<S> secondToLast = null;
            WrappedNode<S> last = head;
            while (last.hasNext()) {
                secondToLast = last;
                last = last.next;
            }
            secondToLast.next = null;
        }
        --size;
    }


    private static class WrappedNode<S> {

        private S element;
        private WrappedNode<S> next;

        public WrappedNode(S element) {
            this.element = element;
        }

        public boolean hasNext() {
            return next != null;
        }
    }
}

package com.baeldung.collections.fixedsizequeues;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FifoFixedSizeQueue<E> extends AbstractQueue<E> {

    /** The queued items */
    final Object[] items;

    /** Number of elements in the queue */
    int count;

    public FifoFixedSizeQueue(int capacity) {
        super();

        items = new Object[capacity];
        count = 0;
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Queue doesn't allow nulls");
        }
        if (count == items.length) {
            this.poll();
        }
        this.items[count] = e;
        count++;
        return true;
    }

    @Override
    public E poll() {
        if (count <= 0) {
            return null;
        }
        E item = (E) items[0];
        shiftLeft();
        count--;
        return item;
    }

    private void shiftLeft() {
        int i = 1;
        while (i < items.length) {
            if (items[i] == null) {
                break;
            }
            items[i - 1] = items[i];
            i++;
        }
    }

    @Override
    public E peek() {
        if (count <= 0) {
            return null;
        }
        return (E) items[0];
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<E> iterator() {
        List<E> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add((E) items[i]);
        }
        return list.iterator();
    }
}

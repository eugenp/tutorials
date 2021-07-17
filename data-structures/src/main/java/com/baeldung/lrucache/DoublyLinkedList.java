package com.baeldung.lrucache;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DoublyLinkedList<T> {

    private DummyNode<T> dummyNode;
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private AtomicInteger size;
    private ReentrantReadWriteLock.ReadLock readLock;
    private ReentrantReadWriteLock.WriteLock writeLock;


    public DoublyLinkedList() {
        this.dummyNode = new DummyNode<T>(this);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
        clear();
    }

    public void clear() {
        writeLock.lock();
        try {
            head = dummyNode;
            tail = dummyNode;
            size = new AtomicInteger(0);
        } finally {
            writeLock.unlock();
        }
    }

    public int size() {
        readLock.lock();
        try {
            return size.get();
        } finally {
            readLock.unlock();
        }
    }

    public boolean isEmpty() {
        readLock.lock();
        try {
            return head.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    public boolean contains(T value) {
        readLock.lock();
        try {
            return search(value).hasElement();
        } finally {
            readLock.unlock();
        }
    }

    public LinkedListNode<T> search(T value) {
        readLock.lock();
        try {
            return head.search(value);
        } finally {
            readLock.unlock();
        }
    }

    public LinkedListNode<T> add(T value) {
        writeLock.lock();
        try {
            head = new Node<T>(value, head, this);
            if (tail.isEmpty()) {
                tail = head;
            }
            size.incrementAndGet();
            return head;
        } finally {
            writeLock.unlock();
        }
    }

    public boolean addAll(Collection<T> values) {
        writeLock.lock();
        try {
            for (T value : values) {
                if (add(value).isEmpty()) {
                    return false;
                }
            }
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    public LinkedListNode<T> remove(T value) {
        writeLock.lock();
        try {
            LinkedListNode<T> linkedListNode = head.search(value);
            if (!linkedListNode.isEmpty()) {
                if (linkedListNode == tail) {
                    tail = tail.getPrev();
                }
                if (linkedListNode == head) {
                    head = head.getNext();
                }
                linkedListNode.detach();
                size.decrementAndGet();
            }
            return linkedListNode;
        } finally {
            writeLock.unlock();
        }
    }

    public LinkedListNode<T> removeTail() {
        writeLock.lock();
        try {
            LinkedListNode<T> oldTail = tail;
            if (oldTail == head) {
                tail = head = dummyNode;
            } else {
                tail = tail.getPrev();
                oldTail.detach();
            }
            if (!oldTail.isEmpty()) {
                size.decrementAndGet();
            }
            return oldTail;
        } finally {
            writeLock.unlock();
        }
    }

    public LinkedListNode<T> moveToFront(LinkedListNode<T> node) {
        return node.isEmpty() ? dummyNode : updateAndMoveToFront(node, node.getElement());
    }

    public LinkedListNode<T> updateAndMoveToFront(LinkedListNode<T> node, T newValue) {
        writeLock.lock();
        try {
            if (node.isEmpty() || (this != (node.getListReference()))) {
                return dummyNode;
            }
            detach(node);
            add(newValue);
            return head;
        } finally {
            writeLock.unlock();
        }
    }

    private void detach(LinkedListNode<T> node) {
        if (node != tail) {
            node.detach();
            if (node == head) {
                head = head.getNext();
            }
            size.decrementAndGet();
        } else {
            removeTail();
        }
    }
}

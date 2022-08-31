package com.baeldung.collections.fixedsizequeues;

public class FifoFixedSizeQueueRemoveOldestInOffer<E> extends FifoFixedSizeQueue<E> {

    public FifoFixedSizeQueueRemoveOldestInOffer(int capacity) {
        super(capacity);
    }

    @Override
    public boolean offer(E e) {
        if (count >= items.length) {
            this.poll();
        }
        return super.offer(e);
    }
}

package com.baeldung.collections.fixedsizequeues;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FifoFixedSizeQueueRemoveOldestInOfferUnitTest {

    @Test
    void givenEmptyQueue_whenIterating_thenHasNextIsFalse() {
        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(10);
        Iterator<String> i = toTest.iterator();
        assertFalse(i.hasNext());
    }

    @Test
    void givenEmptyQueue_whenOffer_thenCountIsOne() {
        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(10);
        toTest.offer("1");
        assertEquals(1, toTest.size());
    }

    @Test
    void givenEmptyQueue_whenPeek_thenNull() {
        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(10);
        String tail = toTest.peek();
        assertNull(tail);
    }

    @Test
    void givenEmptyQueue_whenPoll_thenNull() {
        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(10);
        String dequeued = toTest.poll();
        assertNull(dequeued);
    }

    @Test
    void givenNonEmptyQueue_whenIterating_thenHasNextIsTrue() {
        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(10);
        Iterator<String> i = toTest.iterator();
        assertFalse(i.hasNext());
    }

    @Test
    void givenNonEmptyQueue_whenOffer_thenCountMatchesQueueElements() {
        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(10);
        toTest.offer("1");
        toTest.offer("2");
        assertEquals(2, toTest.size());
    }

    @Test
    void givenNonEmptyQueue_whenPeek_thenFirstElementMustBeReturned() {
        String expectedElement = "1";

        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(10);
        toTest.offer(expectedElement);
        toTest.offer("2");

        assertEquals(expectedElement, toTest.peek());
        assertEquals(expectedElement, toTest.peek());
    }

    @Test
    void givenNonEmptyQueue_whenPoll_thenFirstElementMustBeReturnedAndRemoved() {
        String expectedPoll = "1";
        String expectedTailAfterPoll = "2";

        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(10);
        toTest.offer(expectedPoll);
        toTest.offer(expectedTailAfterPoll);

        assertEquals(expectedPoll, toTest.poll());
        assertEquals(expectedTailAfterPoll, toTest.peek());
    }

    @Test
    void givenFullQueue_whenOffer_thenRemovedOldestFromTailAndOffer() {
        FifoFixedSizeQueueRemoveOldestInOffer<String> toTest = new FifoFixedSizeQueueRemoveOldestInOffer<>(2);
        toTest.offer("1");
        toTest.offer("2");
        toTest.offer("3");

        Iterator<String> i = toTest.iterator();
        assertEquals("2", i.next());
        assertEquals("3", i.next());
        assertFalse(i.hasNext());
    }
}

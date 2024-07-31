package com.baeldung.algorithms.linkedlist;

public class CycleDetectionByFastAndSlowIterators {

    public static <T> CycleDetectionResult<T> detectCycle(Node<T> head) {
        if (head == null) {
            return new CycleDetectionResult<>(false, null);
        }

        Node<T> slow = head;
        Node<T> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return new CycleDetectionResult<>(true, fast);
            }
        }

        return new CycleDetectionResult<>(false, null);
    }

}

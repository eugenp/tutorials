package com.baeldung.algorithms.linkedlist;

public class CycleDetectionByFastAndSlowIterators {

    public static <T> boolean detectCycle(Node<T> head) {
        if (head == null) {
            return false;
        }

        Node<T> slow = head;
        Node<T> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

}

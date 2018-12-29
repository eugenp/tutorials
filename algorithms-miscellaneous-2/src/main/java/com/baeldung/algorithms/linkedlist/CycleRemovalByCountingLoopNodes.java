package com.baeldung.algorithms.linkedlist;

public class CycleRemovalByCountingLoopNodes {

    public static <T> boolean detectAndRemoveCycle(Node<T> head) {
        CycleDetectionResult<T> result = CycleDetectionByFastAndSlowIterators.detectCycle(head);

        if (result.cycleExists) {
            removeCycle(result.node, head);
        }

        return result.cycleExists;
    }

    private static <T> void removeCycle(Node<T> loopNodeParam, Node<T> head) {
        int cycleLength = calculateCycleLength(loopNodeParam);
        Node<T> cycleLengthAdvancedIterator = head;
        Node<T> it = head;

        for (int i = 0; i < cycleLength; i++) {
            cycleLengthAdvancedIterator = cycleLengthAdvancedIterator.next;
        }

        while (it.next != cycleLengthAdvancedIterator.next) {
            it = it.next;
            cycleLengthAdvancedIterator = cycleLengthAdvancedIterator.next;
        }

        cycleLengthAdvancedIterator.next = null;
    }

    private static <T> int calculateCycleLength(Node<T> loopNodeParam) {
        Node<T> loopNode = loopNodeParam;
        int length = 1;

        while (loopNode.next != loopNodeParam) {
            length++;
            loopNode = loopNode.next;
        }

        return length;
    }

}

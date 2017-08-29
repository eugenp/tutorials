package com.baeldung.algorithms.linkedlist;

public class CycleRemovalBruteForce {

    public static <T> boolean detectAndRemoveCycle(Node<T> head) {
        if (head == null) {
            return false;
        }

        Node<T> slow = head;
        Node<T> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                removeCycle(slow, head);
                return true;
            }
        }

        return false;
    }

    private static <T> void removeCycle(Node<T> loopNodeParam, Node<T> head) {
        Node<T> it = head;

        while (it != null) {
            if (isNodeReachableFromLoopNode(it, loopNodeParam)) {
                Node<T> loopStart = it;
                findEndNodeAndBreakCycle(loopStart);
                break;
            }
            it = it.next;
        }
    }

    private static <T> boolean isNodeReachableFromLoopNode(Node<T> it, Node<T> loopNodeParam) {
        Node<T> loopNode = loopNodeParam;

        while (loopNode.next != loopNodeParam) {
            if (it == loopNode) {
                return true;
            }
            loopNode = loopNode.next;
        }

        return false;
    }

    private static <T> void findEndNodeAndBreakCycle(Node<T> loopStartParam) {
        Node<T> loopStart = loopStartParam;

        while (loopStart.next != loopStartParam) {
            loopStart = loopStart.next;
        }

        loopStart.next = null;
    }
}

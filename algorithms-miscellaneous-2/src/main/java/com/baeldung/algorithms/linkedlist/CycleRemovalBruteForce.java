package com.baeldung.algorithms.linkedlist;

public class CycleRemovalBruteForce {

    public static <T> boolean detectAndRemoveCycle(Node<T> head) {
        CycleDetectionResult<T> result = CycleDetectionByFastAndSlowIterators.detectCycle(head);

        if (result.cycleExists) {
            removeCycle(result.node, head);
        }

        return result.cycleExists;
    }

    /**
     * @param loopNodeParam - reference to the node where Flyods cycle 
     * finding algorithm ends, i.e. the fast and the slow iterators
     * meet.
     * @param head - reference to the head of the list
     */
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

        do {
            if (it == loopNode) {
                return true;
            }
            loopNode = loopNode.next;
        } while (loopNode.next != loopNodeParam);

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

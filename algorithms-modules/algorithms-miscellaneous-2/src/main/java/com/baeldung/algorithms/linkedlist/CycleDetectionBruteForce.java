package com.baeldung.algorithms.linkedlist;

public class CycleDetectionBruteForce {

    public static <T> CycleDetectionResult<T> detectCycle(Node<T> head) {
        if (head == null) {
            return new CycleDetectionResult<>(false, null);
        }

        Node<T> it1 = head;
        int nodesTraversedByOuter = 0;
        while (it1 != null && it1.next != null) {
            it1 = it1.next;
            nodesTraversedByOuter++;

            int x = nodesTraversedByOuter;
            Node<T> it2 = head;
            int noOfTimesCurrentNodeVisited = 0;

            while (x > 0) {
                it2 = it2.next;

                if (it2 == it1) {
                    noOfTimesCurrentNodeVisited++;
                }

                if (noOfTimesCurrentNodeVisited == 2) {
                    return new CycleDetectionResult<>(true, it1);
                }

                x--;
            }
        }

        return new CycleDetectionResult<>(false, null);
    }

}

package com.baeldung.algorithms.linkedlist;

import java.util.HashSet;
import java.util.Set;

public class CycleDetectionByHashing {

    public static <T> CycleDetectionResult<T> detectCycle(Node<T> head) {
        if (head == null) {
            return new CycleDetectionResult<>(false, null);
        }

        Set<Node<T>> set = new HashSet<>();
        Node<T> node = head;

        while (node != null) {
            if (set.contains(node)) {
                return new CycleDetectionResult<>(true, node);
            }
            set.add(node);
            node = node.next;
        }

        return new CycleDetectionResult<>(false, null);
    }

}

package com.baeldung.algorithms.middleelementlookup;

import java.util.LinkedList;
import java.util.Optional;

public class MiddleElementLookup {

    public static Optional<String> findMiddleElementLinkedList(LinkedList<String> linkedList) {
        if (linkedList == null || linkedList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(linkedList.get((linkedList.size() - 1) / 2));
    }

    public static Optional<String> findMiddleElementFromHead(Node head) {
        if (head == null) {
            return Optional.empty();
        }

        // calculate the size of the list
        Node current = head;
        int size = 1;
        while (current.hasNext()) {
            current = current.next();
            size++;
        }

        // iterate till the middle element
        current = head;
        for (int i = 0; i < (size - 1) / 2; i++) {
            current = current.next();
        }

        return Optional.ofNullable(current.data());
    }

    public static Optional<String> findMiddleElementFromHead1PassRecursively(Node head) {
        if (head == null) {
            return Optional.empty();
        }

        MiddleAuxRecursion middleAux = new MiddleAuxRecursion();
        findMiddleRecursively(head, middleAux);
        return Optional.ofNullable(middleAux.middle.data());
    }

    private static void findMiddleRecursively(Node node, MiddleAuxRecursion middleAux) {
        if (node == null) {
            // reached the end
            middleAux.length = middleAux.length / 2;
            return;
        }
        middleAux.length++;
        findMiddleRecursively(node.next(), middleAux);

        if (middleAux.length == 0) {
            // found the middle
            middleAux.middle = node;
        }

        middleAux.length--;
    }

    public static Optional<String> findMiddleElementFromHead1PassIteratively(Node head) {
        if (head == null) {
            return Optional.empty();
        }

        Node slowPointer = head;
        Node fastPointer = head;

        while (fastPointer.hasNext() && fastPointer.next()
            .hasNext()) {
            fastPointer = fastPointer.next()
                .next();
            slowPointer = slowPointer.next();
        }

        return Optional.ofNullable(slowPointer.data());
    }

    private static class MiddleAuxRecursion {
        Node middle;
        int length = 0;
    }

}

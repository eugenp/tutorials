package com.baeldung.linkedlist;

import java.util.LinkedList;

import com.baeldung.linkedlist.Node;

public class MiddleElementLookup {

    public static String findMiddleElementLinkedList(LinkedList<String> linkedList) {
        if (linkedList == null || linkedList.isEmpty()) {
            return null;
        }

        return linkedList.get((linkedList.size() - 1) / 2);
    }

    public static String findMiddleElementFromHead(Node head) {
        if (head == null) {
            return null;
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

        return current.data();
    }

    public static String findMiddleElementFromHead1PassRecursively(Node head) {
        if (head == null) {
            return null;
        }

        MiddleAuxRecursion middleAux = new MiddleAuxRecursion();
        findMiddleRecursively(head, middleAux);
        return middleAux.middle.data();
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

    public static String findMiddleElementFromHead1PassIteratively(Node head) {
        if (head == null) {
            return null;
        }

        Node slowPointer = head;
        Node fastPointer = head;

        while (fastPointer.hasNext() && fastPointer.next()
            .hasNext()) {
            fastPointer = fastPointer.next()
                .next();
            slowPointer = slowPointer.next();
        }

        return slowPointer.data();
    }

    private static class MiddleAuxRecursion {
        Node middle;
        int length = 0;
    }

}

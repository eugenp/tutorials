package com.baeldung.linkedlist;

import com.baeldung.linkedlist.LinkedList.Node;

public class MiddleElementLookup {

    public static String findMiddleElement(LinkedList list) {
        if (list == null || list.head() == null) {
            return null;
        }

        int size = 0;

        // calculate the size of the list
        Node current = list.head();
        while (current.hasNext()) {
            size++;
            current = current.next();
        }

        // iterate till the middle element
        current = list.head();
        for (int i = 0; i < size / 2; i++) {
            current = current.next();
        }

        return current.data();
    }

    public static String findMiddleElement1PassRecursively(LinkedList list) {
        if (list == null || list.head() == null) {
            return null;
        }
        
        MiddleAuxRecursion middleAux = new MiddleAuxRecursion();
        findMiddleRecursively(list.head(), middleAux);
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

    public static String findMiddleElement1PassIteratively(LinkedList list) {
        if (list == null || list.head() == null) {
            return null;
        }

        Node slowPointer = list.head();
        Node fastPointer = list.head();

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

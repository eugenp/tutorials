package com.baeldung.algorithms.linkedlist;

public class CycleDetectionTestBase {

    public static Node<Integer> createList() {
        Node<Integer> root = Node.createNewNode(10, null);

        for (int i = 9; i >= 1; --i) {
            Node<Integer> current = Node.createNewNode(i, root);
            root = current;
        }

        return root;
    }

    public static void createLoop(Node<Integer> root) {
        Node<Integer> tail = Node.getTail(root);

        Node<Integer> middle = root;
        for (int i = 1; i <= 4; i++) {
            middle = middle.next;
        }

        tail.next = middle;
    }

}

package com.baeldung.algorithms.linkedlist;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

public class CycleDetectionTestBase {

    @Parameters
    public static Collection<Object[]> getLists() {
        return Arrays.asList(new Object[][] { 
            { createList(), false }, 
            { createListWithLoop(), true },
            { createListWithFullCycle(), true }, 
            { createListWithSingleNodeInCycle(), true } 
        });
    }

    public static Node<Integer> createList() {
        Node<Integer> root = Node.createNewNode(10, null);

        for (int i = 9; i >= 1; --i) {
            Node<Integer> current = Node.createNewNode(i, root);
            root = current;
        }

        return root;
    }

    public static Node<Integer> createListWithLoop() {
        Node<Integer> node = createList();
        createLoop(node);
        return node;
    }

    public static Node<Integer> createListWithFullCycle() {
        Node<Integer> head = createList();
        Node<Integer> tail = Node.getTail(head);
        tail.next = head;
        return head;
    }

    public static Node<Integer> createListWithSingleNodeInCycle() {
        Node<Integer> head = createList();
        Node<Integer> tail = Node.getTail(head);
        tail.next = tail;
        return head;
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

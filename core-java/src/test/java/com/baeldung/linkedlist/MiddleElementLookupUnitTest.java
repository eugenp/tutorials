package com.baeldung.linkedlist;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

public class MiddleElementLookupUnitTest {

    @Test
    public void whenFindingMiddleLinkedList_thenMiddleFound() {
        assertEquals("3", MiddleElementLookup.findMiddleElementLinkedList(createLinkedList(5)));
        assertEquals("2", MiddleElementLookup.findMiddleElementLinkedList(createLinkedList(4)));
    }

    @Test
    public void whenFindingMiddleFromHead_thenMiddleFound() {
        assertEquals("3", MiddleElementLookup.findMiddleElementFromHead(createNodesList(5)));
        assertEquals("2", MiddleElementLookup.findMiddleElementFromHead(createNodesList(4)));
    }

    @Test
    public void whenFindingMiddleFromHead1PassRecursively_thenMiddleFound() {
        assertEquals("3", MiddleElementLookup.findMiddleElementFromHead1PassRecursively(createNodesList(5)));
        assertEquals("2", MiddleElementLookup.findMiddleElementFromHead1PassRecursively(createNodesList(4)));
    }

    @Test
    public void whenFindingMiddleFromHead1PassIteratively_thenMiddleFound() {
        assertEquals("3", MiddleElementLookup.findMiddleElementFromHead1PassIteratively(createNodesList(5)));
        assertEquals("2", MiddleElementLookup.findMiddleElementFromHead1PassIteratively(createNodesList(4)));
    }

    @Test
    public void whenListEmptyOrNull_thenMiddleNull() {
        assertEquals(null, MiddleElementLookup.findMiddleElementLinkedList(null));
        assertEquals(null, MiddleElementLookup.findMiddleElementFromHead(null));
        assertEquals(null, MiddleElementLookup.findMiddleElementFromHead1PassIteratively(null));
        assertEquals(null, MiddleElementLookup.findMiddleElementFromHead1PassRecursively(null));
    }

    private static LinkedList<String> createLinkedList(int n) {
        LinkedList<String> list = new LinkedList<>();

        for (int i = 1; i <= n; i++) {
            list.add(String.valueOf(i));
        }

        return list;
    }

    private static Node createNodesList(int n) {
        Node head = new Node("1");
        Node current = head;

        for (int i = 2; i <= n; i++) {
            Node newNode = new Node(String.valueOf(i));
            current.setNext(newNode);
            current = newNode;
        }

        return head;
    }

}

package com.baeldung.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.baeldung.algorithms.middleelementlookup.MiddleElementLookup;
import com.baeldung.algorithms.middleelementlookup.Node;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

class MiddleElementLookupUnitTest {

    @Test
    void whenFindingMiddleLinkedList_thenMiddleFound() {
        assertEquals("3", MiddleElementLookup
          .findMiddleElementLinkedList(createLinkedList(5))
          .get());
        assertEquals("2", MiddleElementLookup
          .findMiddleElementLinkedList(createLinkedList(4))
          .get());
    }

    @Test
    void whenFindingMiddleFromHead_thenMiddleFound() {
        assertEquals("3", MiddleElementLookup
          .findMiddleElementFromHead(createNodesList(5))
          .get());
        assertEquals("2", MiddleElementLookup
          .findMiddleElementFromHead(createNodesList(4))
          .get());
    }

    @Test
    void whenFindingMiddleFromHead1PassRecursively_thenMiddleFound() {
        assertEquals("3", MiddleElementLookup
          .findMiddleElementFromHead1PassRecursively(createNodesList(5))
          .get());
        assertEquals("2", MiddleElementLookup
          .findMiddleElementFromHead1PassRecursively(createNodesList(4))
          .get());
    }

    @Test
    void whenFindingMiddleFromHead1PassIteratively_thenMiddleFound() {
        assertEquals("3", MiddleElementLookup
          .findMiddleElementFromHead1PassIteratively(createNodesList(5))
          .get());
        assertEquals("2", MiddleElementLookup
          .findMiddleElementFromHead1PassIteratively(createNodesList(4))
          .get());
    }

    @Test
    void whenListEmptyOrNull_thenMiddleNotFound() {
        // null list
        assertFalse(MiddleElementLookup
          .findMiddleElementLinkedList(null)
          .isPresent());
        assertFalse(MiddleElementLookup
          .findMiddleElementFromHead(null)
          .isPresent());
        assertFalse(MiddleElementLookup
          .findMiddleElementFromHead1PassIteratively(null)
          .isPresent());
        assertFalse(MiddleElementLookup
          .findMiddleElementFromHead1PassRecursively(null)
          .isPresent());

        // empty LinkedList
        assertFalse(MiddleElementLookup
          .findMiddleElementLinkedList(new LinkedList<>())
          .isPresent());

        // LinkedList with nulls
        LinkedList<String> nullsList = new LinkedList<>();
        nullsList.add(null);
        nullsList.add(null);
        assertFalse(MiddleElementLookup
          .findMiddleElementLinkedList(nullsList)
          .isPresent());

        // nodes with null values
        assertFalse(MiddleElementLookup
          .findMiddleElementFromHead(new Node(null))
          .isPresent());
        assertFalse(MiddleElementLookup
          .findMiddleElementFromHead1PassIteratively(new Node(null))
          .isPresent());
        assertFalse(MiddleElementLookup
          .findMiddleElementFromHead1PassRecursively(new Node(null))
          .isPresent());
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

package com.baeldung.algorithms.linkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LinkedListReversalUnitTest {
    @Test
    void givenLinkedList_whenIterativeReverse_thenOutputCorrectResult() {
        ListNode head = constructLinkedList();
        ListNode node = head;
        for (int i = 1; i <= 5; i++) {
            assertNotNull(node);
            assertEquals(i, node.getData());
            node = node.getNext();
        }
        LinkedListReversal reversal = new LinkedListReversal();
        node = reversal.reverseList(head);
        for (int i = 5; i >= 1; i--) {
            assertNotNull(node);
            assertEquals(i, node.getData());
            node = node.getNext();
        }
    }

    @Test
    void givenLinkedList_whenRecursiveReverse_thenOutputCorrectResult() {
        ListNode head = constructLinkedList();
        ListNode node = head;
        for (int i = 1; i <= 5; i++) {
            assertNotNull(node);
            assertEquals(i, node.getData());
            node = node.getNext();
        }
        LinkedListReversal reversal = new LinkedListReversal();
        node = reversal.reverseListRecursive(head);
        for (int i = 5; i >= 1; i--) {
            assertNotNull(node);
            assertEquals(i, node.getData());
            node = node.getNext();
        }
    }

    private ListNode constructLinkedList() {
        ListNode head = null;
        ListNode tail = null;
        for (int i = 1; i <= 5; i++) {
            ListNode node = new ListNode(i);
            if (head == null) {
                head = node;
            } else {
                tail.setNext(node);
            }
            tail = node;
        }
        return head;
    }
}

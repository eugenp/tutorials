package com.baeldung.customlinkedlist;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ListNodeUnitTest {

    @Test
    public void givenLinkedList_whenBuildingReferenceBetweenNodes_thenCorrect() {
        ListNode listNode0 = new ListNode(1);
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(3);

        listNode0.next = listNode1;
        listNode1.next = listNode2;

        assertEquals(1, listNode0.value);
        assertEquals(2, listNode0.next.value);
        assertEquals(3, listNode0.next.next.value);
        assertNull(listNode0.next.next.next);
    }

    @Test
    public void givenLinkedList_whenInsertingNode_thenCorrect() {
        ListNode listNode0 = new ListNode(1);
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(3);
        ListNode newNode = new ListNode(4);
        ListNode newNode2 = new ListNode(5);

        listNode0.next = listNode1;
        listNode1.next = listNode2;

        listNode0.insert(listNode0, newNode);
        listNode0.insert(listNode0, newNode2);

        assertEquals(1, listNode0.value);
        assertEquals(5, listNode0.next.value);
        assertEquals(4, listNode0.next.next.value);
        assertEquals(2, listNode0.next.next.next.value);
        assertEquals(3, listNode0.next.next.next.next.value);
        assertNull(listNode0.next.next.next.next.next);
    }

    @Test
    public void givenLinkedList_whenRemovingNode_thenCorrect() {
        ListNode listNode0 = new ListNode(1);
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(3);

        listNode0.next = listNode1;
        listNode1.next = listNode2;

        listNode0.remove(listNode0);

        assertEquals(1, listNode0.value);
        assertEquals(3, listNode0.next.value);
        assertNull(listNode0.next.next);
    }

    @Test
    public void givenLinkedList_whenGettingANodeThroughIndex_thenCorrect() {
        ListNode listNode0 = new ListNode(1);
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(3);

        listNode0.next = listNode1;
        listNode1.next = listNode2;

        ListNode resultNode = listNode0.get(listNode0, 1);

        assertEquals(2, resultNode.value);
    }

    @Test
    public void givenLinkedList_whenFindingNodeByValue_thenCorrect() {
        ListNode listNode0 = new ListNode(1);
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(3);

        listNode0.next = listNode1;
        listNode1.next = listNode2;

        int index = listNode0.find(listNode0, 3);

        assertEquals(2, index);
    }
}
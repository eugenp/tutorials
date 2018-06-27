package com.baeldung.junit5.runfromjava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.junit.runfromjava.listnode.ListNode;
import com.baeldung.junit.runfromjava.listnode.RemovedNthElement;

class RemovedNthElementTest {

    private ListNode listNode;
    private RemovedNthElement removedNthElement;

    @BeforeEach
    void setUp() throws Exception {
        removedNthElement = new RemovedNthElement();
        listNode = new ListNode(42, new ListNode(666, new ListNode(15, new ListNode(3, null))));
    }

    @Test
    void whenRemovingSecondElement_thenReturnExpectedList() {
        assertEquals(removedNthElement.removeNthFromEnd(listNode, 2)
            .toString(), "42->666->3");
    }

    @Test
    void whenRemovingThirdElement_thenReturnExpectedList() {
        assertEquals(removedNthElement.removeNthFromEnd(listNode, 3)
            .toString(), "42->15->3");
    }
}

package com.baeldung.junit4.runfromjava;

import org.junit.Test;

import com.baeldung.junit.runfromjava.listnode.ListNode;
import com.baeldung.junit.runfromjava.listnode.RemovedNthElement;

import junit.framework.TestCase;

class RemovedNthElementTest extends TestCase {

    private ListNode listNode;
    private RemovedNthElement removedNthElement;

    public RemovedNthElementTest() {
    }

    public RemovedNthElementTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        removedNthElement = new RemovedNthElement();
        listNode = new ListNode(42, new ListNode(666, new ListNode(15, new ListNode(3, null))));
    }

    @Test
    public void whenRemovingSecondElement_thenReturnExpectedList() {
        assertEquals(removedNthElement.removeNthFromEnd(listNode, 2)
            .toString(), "42->666->3");
    }

    @Test
    public void whenRemovingThirdElement_thenReturnExpectedList() {
        assertEquals(removedNthElement.removeNthFromEnd(listNode, 3)
            .toString(), "42->15->3");
    }
}

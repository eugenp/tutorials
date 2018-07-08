package com.baeldung.junit4.runfromjava;

import org.junit.Test;

import com.baeldung.junit.runfromjava.listnode.ListNode;
import com.baeldung.junit.runfromjava.listnode.RemovedNthElement;

import junit.framework.TestCase;

public class RemovedNthElementUnitTest extends TestCase {

    private ListNode listNode;
    private RemovedNthElement removedNthElement;

    public RemovedNthElementUnitTest() {
    }

    public RemovedNthElementUnitTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        removedNthElement = new RemovedNthElement();
        listNode = new ListNode(42, new ListNode(666, new ListNode(15, new ListNode(3, null))));
    }

    @Test
    public void testwhenRemovingSecondElement_thenReturnExpectedList() {
        assertEquals(removedNthElement.removeNthFromEnd(listNode, 2)
            .toString(), "42->666->3");
    }

    @Test
    public void testwhenRemovingThirdElement_thenReturnExpectedList() {
        assertEquals(removedNthElement.removeNthFromEnd(listNode, 3)
            .toString(), "42->15->3");
    }
}

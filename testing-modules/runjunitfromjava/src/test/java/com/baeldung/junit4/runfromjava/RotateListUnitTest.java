package com.baeldung.junit4.runfromjava;

import org.junit.Test;

import com.baeldung.junit.runfromjava.listnode.ListNode;
import com.baeldung.junit.runfromjava.listnode.RotateList;

import junit.framework.TestCase;

public class RotateListUnitTest extends TestCase {
    private RotateList rotateList;
    private ListNode listNode;

    public RotateListUnitTest() {
    }

    public RotateListUnitTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        rotateList = new RotateList();
        listNode = new ListNode(42, new ListNode(666, new ListNode(15, new ListNode(3, null))));
    }

    @Test
    public void testwhenRotatingListTwice_thenReturnExpectedList() {
        assertEquals(rotateList.rotateRight(listNode, 2)
            .toString(), "15->3->42->666");
    }

    @Test
    public void testwhenRotatingListThreeTimes_thenReturnExpectedList() {
        assertEquals(rotateList.rotateRight(listNode, 3)
            .toString(), "666->15->3->42");
    }
}

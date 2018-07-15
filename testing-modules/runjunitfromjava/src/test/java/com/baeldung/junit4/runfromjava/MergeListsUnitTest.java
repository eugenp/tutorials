package com.baeldung.junit4.runfromjava;

import org.junit.Test;

import com.baeldung.junit.runfromjava.listnode.ListNode;
import com.baeldung.junit.runfromjava.listnode.MergeLists;

import static org.junit.Assert.*;
import org.junit.Before;

public class MergeListsUnitTest {

    private ListNode listNode1;
    private ListNode listNode2;
    private MergeLists mergeLists;

    @Before
    public void setUp() throws Exception {
        mergeLists = new MergeLists();
        listNode1 = new ListNode(2, new ListNode(4, new ListNode(6, new ListNode(8, null))));
        listNode2 = new ListNode(1, new ListNode(3, new ListNode(5, new ListNode(7, null))));
    }

    @Test
    public void whenMergingNormalLists_thenGetExpectedString() {
        assertEquals(mergeLists.merge(listNode1, listNode2)
            .toString(), "1->2->3->4->5->6->7->8");
    }

    @Test
    public void whenMergingNullLists_thenGetNull() {
        listNode1 = null;
        listNode2 = null;
        assertNull(mergeLists.merge(listNode1, listNode2));
    }
}

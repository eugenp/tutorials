package com.baeldung.junit4.runfromjava;

import org.junit.Test;

import com.baeldung.junit.runfromjava.listnode.ListNode;

import static org.junit.Assert.*;


public class ListNodeUnitTest  {

    @Test
    public void whenListHasOneElement_thenGetExpectedValue() {
        ListNode listNode = new ListNode(42);
        assertEquals(listNode.getValue(), 42);
    }

    @Test
    public void whenInitSimpleList_thenGettersGiveExpectedValues() {
        ListNode listNode = new ListNode(42, new ListNode(666, null));
        assertEquals(listNode.getValue(), 42);
        assertEquals(listNode.getNext()
            .getValue(), 666);
    }

    @Test
    public void whenConvertingListToString_thenGetExpectedValue() {
        ListNode listNode = new ListNode(42, new ListNode(666, new ListNode(15, null)));
        assertEquals(listNode.toString(), "42->666->15");
    }

}

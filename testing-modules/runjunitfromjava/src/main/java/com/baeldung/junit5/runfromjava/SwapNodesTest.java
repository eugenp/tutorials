package com.baeldung.junit5.runfromjava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.junit.runfromjava.listnode.ListNode;
import com.baeldung.junit.runfromjava.listnode.SwapNodes;

class SwapNodesTest {
    private SwapNodes swapNodes;
    private ListNode listNode;

    @BeforeEach
    void setUp() throws Exception {
        swapNodes = new SwapNodes();
        listNode = new ListNode(42, new ListNode(666, new ListNode(15, new ListNode(3, null))));
    }

    @Test
    void whenSwappingPairs_thenReturnExpectedList() {
        assertEquals(swapNodes.swapPairs(listNode)
            .toString(), "666->42->3->15");
    }

}

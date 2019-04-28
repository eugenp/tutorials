package com.baeldung.math.linkedlist;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class CycleRemovalByCountingLoopNodesUnitTest extends CycleDetectionTestBase {
    boolean cycleExists;
    Node<Integer> head;

    public CycleRemovalByCountingLoopNodesUnitTest(Node<Integer> head, boolean cycleExists) {
        super();
        this.cycleExists = cycleExists;
        this.head = head;
    }

    @Test
    public void givenList_ifLoopExists_thenDetectAndRemoveLoop() {
        Assert.assertEquals(cycleExists, CycleRemovalByCountingLoopNodes.detectAndRemoveCycle(head));
        Assert.assertFalse(CycleDetectionByFastAndSlowIterators.detectCycle(head).cycleExists);
    }
}

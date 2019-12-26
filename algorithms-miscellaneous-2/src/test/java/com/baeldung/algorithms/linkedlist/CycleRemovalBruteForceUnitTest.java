package com.baeldung.algorithms.linkedlist;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class CycleRemovalBruteForceUnitTest extends CycleDetectionTestBase {
    boolean cycleExists;
    Node<Integer> head;

    public CycleRemovalBruteForceUnitTest(Node<Integer> head, boolean cycleExists) {
        super();
        this.cycleExists = cycleExists;
        this.head = head;
    }

    @Test
    public void givenList_ifLoopExists_thenDetectAndRemoveLoop() {
        Assert.assertEquals(cycleExists, CycleRemovalBruteForce.detectAndRemoveCycle(head));
        Assert.assertFalse(CycleDetectionByFastAndSlowIterators.detectCycle(head).cycleExists);
    }
}

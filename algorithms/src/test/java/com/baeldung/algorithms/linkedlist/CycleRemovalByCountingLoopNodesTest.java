package com.baeldung.algorithms.linkedlist;

import org.junit.Assert;
import org.junit.Test;

public class CycleRemovalByCountingLoopNodesTest extends CycleDetectionTestBase {
    
    @Test
    public void givenNormalList_dontDetectLoop() {
        Node<Integer> root = createList();
        Assert.assertFalse(CycleRemovalByCountingLoopNodes.detectAndRemoveCycle(root));
    }
    
    @Test
    public void givenCyclicList_detectAndRemoveLoop() {
        Node<Integer> root = createList();
        createLoop(root);
        Assert.assertTrue(CycleRemovalByCountingLoopNodes.detectAndRemoveCycle(root));
        Assert.assertFalse(CycleDetectionByFastAndSlowIterators.detectCycle(root));
    }

}

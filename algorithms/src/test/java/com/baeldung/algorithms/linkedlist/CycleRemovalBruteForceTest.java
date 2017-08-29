package com.baeldung.algorithms.linkedlist;

import org.junit.Assert;
import org.junit.Test;

public class CycleRemovalBruteForceTest extends CycleDetectionTestBase {
    
    @Test
    public void givenNormalList_dontDetectLoop() {
        Node<Integer> root = createList();
        Assert.assertFalse(CycleRemovalBruteForce.detectAndRemoveCycle(root));
    }
    
    @Test
    public void givenCyclicList_detectAndRemoveLoop() {
        Node<Integer> root = createList();
        createLoop(root);
        Assert.assertTrue(CycleRemovalBruteForce.detectAndRemoveCycle(root));
        Assert.assertFalse(CycleDetectionByFastAndSlowIterators.detectCycle(root));
    }
}

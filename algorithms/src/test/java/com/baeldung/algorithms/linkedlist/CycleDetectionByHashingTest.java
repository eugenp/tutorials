package com.baeldung.algorithms.linkedlist;

import org.junit.Assert;
import org.junit.Test;

public class CycleDetectionByHashingTest extends CycleDetectionTestBase {

    @Test
    public void givenNormalList_dontDetectLoop() {
        Node<Integer> root = createList();
        Assert.assertFalse(CycleDetectionByHashing.detectCycle(root));
    }

    @Test
    public void givenCyclicList_detectLoop() {
        Node<Integer> root = createList();
        createLoop(root);
        Assert.assertTrue(CycleDetectionByHashing.detectCycle(root));
    }
}

package com.baeldung.algorithms.linkedlist;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class CycleDetectionByHashingUnitTest extends CycleDetectionTestBase {
    boolean cycleExists;
    Node<Integer> head;

    public CycleDetectionByHashingUnitTest(Node<Integer> head, boolean cycleExists) {
        super();
        this.cycleExists = cycleExists;
        this.head = head;
    }

    @Test
    public void givenList_detectLoop() {
        Assert.assertEquals(cycleExists, CycleDetectionByHashing.detectCycle(head).cycleExists);
    }
}

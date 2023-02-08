package com.baeldung.algorithms.linkedlist;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class CycleRemovalBruteForceUnitTest extends CycleDetectionTestBase {

    @ParameterizedTest
    @MethodSource("getLists")
    void givenList_ifLoopExists_thenDetectAndRemoveLoop(Node<Integer> head, boolean cycleExists) {
        assertEquals(cycleExists, CycleRemovalBruteForce.detectAndRemoveCycle(head));
        assertFalse(CycleDetectionByFastAndSlowIterators.detectCycle(head).cycleExists);
    }
}

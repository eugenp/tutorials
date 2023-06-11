package com.baeldung.algorithms.minimax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MinimaxUnitTest {
    private Tree gameTree;
    private MiniMax miniMax;

    @BeforeEach
    public void initMiniMaxUtility() {
        miniMax = new MiniMax();
    }

    @Test
    void givenMiniMax_whenConstructTree_thenNotNullTree() {
        assertNull(gameTree);
        miniMax.constructTree(6);
        gameTree = miniMax.getTree();
        assertNotNull(gameTree);
    }

    @Test
    void givenMiniMax_whenCheckWin_thenComputeOptimal() {
        miniMax.constructTree(6);
        boolean result = miniMax.checkWin();
        assertTrue(result);
        miniMax.constructTree(8);
        result = miniMax.checkWin();
        assertFalse(result);
    }
}

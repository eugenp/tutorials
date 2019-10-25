package com.baeldung.algorithms.balancedbinarytree;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BalancedBinaryTreeUnitTest extends BinaryTreeDataProvider {

    @Test
    public void testIsBalanced_balanced() {
        for (Tree tree : balancedTrees()) {
            assertTrue(BalancedBinaryTree.isBalanced(tree));//, tree.toString() + " should be balanced");
        }
    }

    @Test
    public void testIsBalanced_unbalanced() {
        for (Tree tree : unbalancedTrees()) {
            System.out.println("wosiaku!");
            assertFalse(BalancedBinaryTree.isBalanced(tree));//, tree.toString() + " should not be balanced");
        }
    }
}

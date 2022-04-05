package com.baeldung.algorithms.balancedbinarytree;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BalancedBinaryTreeUnitTest extends BinaryTreeDataProvider {

    @Test
    public void givenBalancedTrees_whenCallingIsBalanced_ShouldReturnTrue() {
        for (Tree tree : balancedTrees()) {
            assertTrue(toString(tree) + " should be balanced", BalancedBinaryTree.isBalanced(tree));
        }
    }

    @Test
    public void givenUnbalancedTrees_whenCallingIsBalanced_ShouldReturnFalse() {
        for (Tree tree : unbalancedTrees()) {
            assertFalse(toString(tree) + " should not be balanced", BalancedBinaryTree.isBalanced(tree));
        }
    }

    private String toString(Tree tree) {
        return tree != null ? tree.toString() : "null";
    }
}

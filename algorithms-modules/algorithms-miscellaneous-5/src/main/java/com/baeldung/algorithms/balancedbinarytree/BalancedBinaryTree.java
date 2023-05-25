package com.baeldung.algorithms.balancedbinarytree;

public class BalancedBinaryTree {

    public static boolean isBalanced(Tree tree) {
        return isBalancedRecursive(tree, -1).isBalanced;
    }

    private static Result isBalancedRecursive(Tree tree, int depth) {
        if (tree == null) {
            return new Result(true, -1);
        }

        Result leftSubtreeResult = isBalancedRecursive(tree.left(), depth + 1);
        Result rightSubtreeResult = isBalancedRecursive(tree.right(), depth + 1);

        boolean isBalanced = Math.abs(leftSubtreeResult.height - rightSubtreeResult.height) <= 1;
        boolean subtreesAreBalanced = leftSubtreeResult.isBalanced && rightSubtreeResult.isBalanced;
        int height = Math.max(leftSubtreeResult.height, rightSubtreeResult.height) + 1;

        return new Result(isBalanced && subtreesAreBalanced, height);
    }

    private static final class Result {
        private final boolean isBalanced;
        private final int height;

        private Result(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }
}

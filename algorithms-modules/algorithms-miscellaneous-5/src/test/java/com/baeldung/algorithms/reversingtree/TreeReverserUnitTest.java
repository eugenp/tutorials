package com.baeldung.algorithms.reversingtree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TreeReverserUnitTest {

    @Test
    void givenTreeWhenReversingRecursivelyThenReversed() {
        TreeReverser reverser = new TreeReverser();

        TreeNode treeNode = createBinaryTree();

        reverser.reverseRecursive(treeNode);

        assertEquals("4 7 9 6 2 3 1", reverser.toString(treeNode)
            .trim());
    }

    @Test
    void givenTreeWhenReversingIterativelyThenReversed() {
        TreeReverser reverser = new TreeReverser();

        TreeNode treeNode = createBinaryTree();

        reverser.reverseIterative(treeNode);

        assertEquals("4 7 9 6 2 3 1", reverser.toString(treeNode)
            .trim());
    }

    private TreeNode createBinaryTree() {

        TreeNode leaf1 = new TreeNode(1);
        TreeNode leaf2 = new TreeNode(3);
        TreeNode leaf3 = new TreeNode(6);
        TreeNode leaf4 = new TreeNode(9);

        TreeNode nodeRight = new TreeNode(7, leaf3, leaf4);
        TreeNode nodeLeft = new TreeNode(2, leaf1, leaf2);

        TreeNode root = new TreeNode(4, nodeLeft, nodeRight);

        return root;
    }
}

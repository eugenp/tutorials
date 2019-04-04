package com.baeldung.algorithms.reversingtree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TreeReverserUnitTest {

    @Test
    public void givenTreeWhenReversingRecursivelyThenReversed() {
        TreeReverser reverser = new TreeReverser();

        TreeNode treeNode = reverser.createBinaryTree();

        reverser.reverseRecursive(treeNode);

        assertEquals("4 7 9 6 2 3 1", reverser.toString(treeNode)
            .trim());
    }

    @Test
    public void givenTreeWhenReversingIterativelyThenReversed() {
        TreeReverser reverser = new TreeReverser();

        TreeNode treeNode = reverser.createBinaryTree();

        reverser.reverseIterative(treeNode);

        assertEquals("4 7 9 6 2 3 1", reverser.toString(treeNode)
            .trim());
    }

}

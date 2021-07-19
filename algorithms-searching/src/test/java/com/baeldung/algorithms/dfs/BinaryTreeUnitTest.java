package com.baeldung.algorithms.dfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BinaryTreeUnitTest {

    @Test
    public void givenABinaryTree_WhenAddingElements_ThenTreeNotEmpty() {

        BinaryTree bt = createBinaryTree();

        assertTrue(!bt.isEmpty());
    }

    @Test
    public void givenABinaryTree_WhenAddingElements_ThenTreeContainsThoseElements() {

        BinaryTree bt = createBinaryTree();

        assertTrue(bt.containsNode(6));
        assertTrue(bt.containsNode(4));

        assertFalse(bt.containsNode(1));
    }

    @Test
    public void givenABinaryTree_WhenAddingExistingElement_ThenElementIsNotAdded() {

        BinaryTree bt = createBinaryTree();

        int initialSize = bt.getSize();

        assertTrue(bt.containsNode(3));
        bt.add(3);
        assertEquals(initialSize, bt.getSize());
    }

    @Test
    public void givenABinaryTree_WhenLookingForNonExistingElement_ThenReturnsFalse() {

        BinaryTree bt = createBinaryTree();

        assertFalse(bt.containsNode(99));
    }

    @Test
    public void givenABinaryTree_WhenDeletingElements_ThenTreeDoesNotContainThoseElements() {

        BinaryTree bt = createBinaryTree();

        assertTrue(bt.containsNode(9));
        bt.delete(9);
        assertFalse(bt.containsNode(9));
    }

    @Test
    public void givenABinaryTree_WhenDeletingNonExistingElement_ThenTreeDoesNotDelete() {

        BinaryTree bt = createBinaryTree();

        int initialSize = bt.getSize();

        assertFalse(bt.containsNode(99));
        bt.delete(99);
        assertFalse(bt.containsNode(99));
        assertEquals(initialSize, bt.getSize());
    }

    @Test
    public void it_deletes_the_root() {
        int value = 12;
        BinaryTree bt = new BinaryTree();
        bt.add(value);

        assertTrue(bt.containsNode(value));
        bt.delete(value);
        assertFalse(bt.containsNode(value));
    }

    @Test
    public void givenABinaryTree_WhenTraversingInOrder_ThenPrintValues() {

        BinaryTree bt = createBinaryTree();

        bt.traverseInOrder(bt.root);
        System.out.println();
        bt.traverseInOrderWithoutRecursion();
    }

    @Test
    public void givenABinaryTree_WhenTraversingPreOrder_ThenPrintValues() {

        BinaryTree bt = createBinaryTree();

        bt.traversePreOrder(bt.root);
        System.out.println();
        bt.traversePreOrderWithoutRecursion();
    }

    @Test
    public void givenABinaryTree_WhenTraversingPostOrder_ThenPrintValues() {

        BinaryTree bt = createBinaryTree();

        bt.traversePostOrder(bt.root);
        System.out.println();
        bt.traversePostOrderWithoutRecursion();
    }

    @Test
    public void givenABinaryTree_WhenTraversingLevelOrder_ThenPrintValues() {

        BinaryTree bt = createBinaryTree();

        bt.traverseLevelOrder();
    }

    private BinaryTree createBinaryTree() {
        BinaryTree bt = new BinaryTree();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

        return bt;
    }

}

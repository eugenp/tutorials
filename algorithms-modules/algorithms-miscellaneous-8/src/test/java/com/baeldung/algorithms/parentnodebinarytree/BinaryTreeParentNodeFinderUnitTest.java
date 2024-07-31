package com.baeldung.algorithms.parentnodebinarytree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

class BinaryTreeParentNodeFinderUnitTest {

    private TreeNode subject;

    @BeforeEach
    void setUp() {
        subject = new TreeNode(8);
        subject.insert(5);
        subject.insert(12);
        subject.insert(3);
        subject.insert(7);
        subject.insert(1);
        subject.insert(4);
        subject.insert(11);
        subject.insert(14);
        subject.insert(13);
        subject.insert(16);
    }

    @Test
    void givenBinaryTree_whenFindParentNode_thenReturnCorrectParentNode() {
        assertEquals(8, subject.parent(5).value);
        assertEquals(5, subject.parent(3).value);
        assertEquals(5, subject.parent(7).value);
        assertEquals(3, subject.parent(4).value);
        assertEquals(3, subject.parent(1).value);
        assertEquals(8, subject.parent(12).value);
        assertEquals(12, subject.parent(14).value);
        assertEquals(12, subject.parent(11).value);
        assertEquals(14, subject.parent(16).value);
        assertEquals(14, subject.parent(13).value);
        assertThrows(NoSuchElementException.class, () -> subject.parent(1231));
        assertThrows(NoSuchElementException.class, () -> subject.parent(8));
    }

    @Test
    void givenBinaryTree_whenFindParentNodeIteratively_thenReturnCorrectParentNode() {
        assertEquals(8, subject.iterativeParent(5).value);
        assertEquals(5, subject.iterativeParent(3).value);
        assertEquals(5, subject.iterativeParent(7).value);
        assertEquals(3, subject.iterativeParent(4).value);
        assertEquals(3, subject.iterativeParent(1).value);
        assertEquals(8, subject.iterativeParent(12).value);
        assertEquals(12, subject.iterativeParent(14).value);
        assertEquals(12, subject.iterativeParent(11).value);
        assertEquals(14, subject.iterativeParent(16).value);
        assertEquals(14, subject.iterativeParent(13).value);
        assertThrows(NoSuchElementException.class, () -> subject.iterativeParent(1231));
        assertThrows(NoSuchElementException.class, () -> subject.iterativeParent(8));
    }

    @Test
    void givenParentKeeperBinaryTree_whenGetParent_thenReturnCorrectParent() {
        ParentKeeperTreeNode subject = new ParentKeeperTreeNode(8);
        subject.insert(5);
        subject.insert(12);
        subject.insert(3);
        subject.insert(7);
        subject.insert(1);
        subject.insert(4);
        subject.insert(11);
        subject.insert(14);
        subject.insert(13);
        subject.insert(16);

        assertNull(subject.parent);
        assertEquals(8, subject.left.parent.value);
        assertEquals(8, subject.right.parent.value);
        assertEquals(5, subject.left.left.parent.value);
        assertEquals(5, subject.left.right.parent.value);

        // tests for other nodes
    }
}
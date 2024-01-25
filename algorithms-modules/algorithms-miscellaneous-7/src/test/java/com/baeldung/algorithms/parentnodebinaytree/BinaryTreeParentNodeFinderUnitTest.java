package com.baeldung.algorithms.parentnodebinaytree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeParentNodeFinderUnitTest {

    private TreeNode target;
    @BeforeEach
    void setUp() {
        target = new TreeNode(8);
        target.insert(5); target.insert(12);
        target.insert(3); target.insert(7);
        target.insert(1); target.insert(4);
        target.insert(11); target.insert(14);
        target.insert(13); target.insert(16);
    }

    @Test
    void givenBinaryTree_andNodeTree_whenFindParentNode_thenReturnCorrectParentNode() {
        assertEquals(8, target.parent(5).value);
        assertEquals(5, target.parent(3).value);
        assertEquals(5, target.parent(7).value);
        assertEquals(3, target.parent(4).value);
        assertEquals(3, target.parent(1).value);
        assertEquals(8, target.parent(12).value);
        assertEquals(12, target.parent(14).value);
        assertEquals(12, target.parent(11).value);
        assertEquals(14, target.parent(16).value);
        assertEquals(14, target.parent(13).value);
        assertThrows(NoSuchElementException.class, () -> target.parent(1231));
        assertThrows(NoSuchElementException.class, () -> target.parent(8));
    }
}
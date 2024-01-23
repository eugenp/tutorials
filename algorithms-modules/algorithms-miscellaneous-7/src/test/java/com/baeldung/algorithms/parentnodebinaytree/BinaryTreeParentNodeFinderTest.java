package com.baeldung.algorithms.parentnodebinaytree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeParentNodeFinderTest {

    @Test
    void givenBinaryTree_andNodeTree_whenFindParentNode_thenReturnCorrectParentNode() {

        TreeNode bst = new TreeNode(6);
        bst.insert(8);
        bst.insert(5);
        bst.insert(3);
        bst.insert(2);
        bst.insert(4);

        System.out.println(bst);
    }
}
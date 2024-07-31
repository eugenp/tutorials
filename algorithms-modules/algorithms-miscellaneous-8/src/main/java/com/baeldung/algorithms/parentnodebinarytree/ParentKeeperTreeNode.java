package com.baeldung.algorithms.parentnodebinarytree;

import java.util.Objects;

public class ParentKeeperTreeNode {

    int value;
    ParentKeeperTreeNode parent;
    ParentKeeperTreeNode left;
    ParentKeeperTreeNode right;

    public ParentKeeperTreeNode(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentKeeperTreeNode treeNode = (ParentKeeperTreeNode) o;
        return value == treeNode.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public void insert(int value) {
        insert(this, value);
    }

    private void insert(ParentKeeperTreeNode currentNode, final int value) {
        if (currentNode.left == null && value < currentNode.value) {
            currentNode.left = new ParentKeeperTreeNode(value);
            currentNode.left.parent = currentNode;
            return;
        }

        if (currentNode.right == null && value > currentNode.value) {
            currentNode.right = new ParentKeeperTreeNode(value);
            currentNode.right.parent = currentNode;
            return;
        }

        if (value > currentNode.value) {
            insert(currentNode.right, value);
        }

        if (value < currentNode.value) {
            insert(currentNode.left, value);
        }
    }
}

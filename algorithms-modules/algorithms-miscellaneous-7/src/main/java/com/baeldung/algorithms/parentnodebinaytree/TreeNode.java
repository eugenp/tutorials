package com.baeldung.algorithms.parentnodebinaytree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.lang.String.format;

public class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    public TreeNode(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return value == treeNode.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public void insert(int value) {
        insert(this, value);
    }

    private void insert(TreeNode currentNode, final int value) {
        if (currentNode.left == null && value < currentNode.value) {
            currentNode.left = new TreeNode(value);
            return;
        }

        if (currentNode.right == null && value > currentNode.value) {
            currentNode.right = new TreeNode(value);
            return;
        }

        if (value > currentNode.value) {
            insert(currentNode.right, value);
        }

        if (value < currentNode.value) {
            insert(currentNode.left, value);
        }
    }

    public TreeNode parent(int target) throws NoSuchElementException {
        return parent(this, new TreeNode(target));
    }

    private TreeNode parent(TreeNode current, TreeNode target) throws NoSuchElementException {
        final boolean isTopmostRoot = target.equals(current);
        final boolean nodeDoesNotExist = current == null;

        if (isTopmostRoot || nodeDoesNotExist) {
            throw new NoSuchElementException(format("No parent node found for 'target.value=%s' " +
                            "The target is not in the tree or the target is the topmost root node.",
                    target.value));
        }

        final boolean isTargetChildOfCurrent = target.equals(current.left)
                || target.equals(current.right);

        if (isTargetChildOfCurrent) {
            return current;
        }

        if (target.value < current.value) {
            return parent(current.left, target);
        }

        return parent(current.right, target);
    }

    public TreeNode iterativeParent(int target) {
        return iterativeParent(this, new TreeNode(target));
    }
    private TreeNode iterativeParent(TreeNode current, TreeNode target) {
        Deque<TreeNode> visitedNodes = new LinkedList<>();

        while (current != null || !visitedNodes.isEmpty()) {

            while (current != null) {
                visitedNodes.addFirst(current);
                current = current.left;
            }

            current = visitedNodes.pollFirst();

            if (target.equals(current.left) || target.equals(current.right)) {
                return current;
            }

            current = current.right;
        }

        throw new NoSuchElementException();
    }
}

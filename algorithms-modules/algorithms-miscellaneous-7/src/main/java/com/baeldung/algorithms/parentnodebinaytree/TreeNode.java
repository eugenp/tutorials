package com.baeldung.algorithms.parentnodebinaytree;

public class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    public TreeNode(int value) {
        this.value = value;
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

    public TreeNode parent(TreeNode target) {
        return parent(this, target);
    }

    private TreeNode parent(TreeNode root, TreeNode target) {
        if (root == null) {
            return null;
        } else {
            if (root.left == target || root.right == target) {
                return root;
            }

            if (root.value < root.left.value) {
                return parent(root.left, target);
            } else {
                return parent(root.right, target);
            }
        }
    }
}

package com.baeldung.algorithms.reversingtree;

import java.util.LinkedList;

public class TreeReverser {

    public void reverseRecursive(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }

        TreeNode temp = treeNode.getLeftChild();
        treeNode.setLeftChild(treeNode.getRightChild());
        treeNode.setRightChild(temp);

        reverseRecursive(treeNode.getLeftChild());
        reverseRecursive(treeNode.getRightChild());
    }

    public void reverseIterative(TreeNode treeNode) {
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();

        if (treeNode != null) {
            queue.add(treeNode);
        }

        while (!queue.isEmpty()) {

            TreeNode node = queue.poll();
            if (node.getLeftChild() != null)
                queue.add(node.getLeftChild());
            if (node.getRightChild() != null)
                queue.add(node.getRightChild());

            TreeNode temp = node.getLeftChild();
            node.setLeftChild(node.getRightChild());
            node.setRightChild(temp);
        }
    }

    public String toString(TreeNode root) {
        if (root == null) {
            return "";
        }

        StringBuffer buffer = new StringBuffer(String.valueOf(root.getValue())).append(" ");

        buffer.append(toString(root.getLeftChild()));
        buffer.append(toString(root.getRightChild()));

        return buffer.toString();
    }
}

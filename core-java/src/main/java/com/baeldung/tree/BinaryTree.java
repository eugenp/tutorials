package com.baeldung.tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {

    Node root;

    public void add(int value) {

        Node newNode = new Node(value);

        if (root == null) {
            root = newNode;
            return;
        }

        Node parent = root;
        Node current = root;

        while (true) {

            if (newNode.value < parent.value) {
                current = parent.left;

                if (current == null) {
                    parent.left = newNode;
                    break;
                }
            } else {
                current = parent.right;

                if (current == null) {
                    parent.right = newNode;
                    break;
                }
            }

            parent = current;
        }

    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean containsNode(int value) {

        Node current = root;

        while (current != null) {

            if (value == current.value) {
                return true;
            }

            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }

        }

        return false;
    }

    public void delete(int value) {

        Node current = root;
        Node parent = root;
        Node nodeToDelete = null;
        boolean isLeftChild = false;

        while (nodeToDelete == null && current != null) {

            if (value == current.value) {
                nodeToDelete = current;
            } else if (value < current.value) {
                parent = current;
                current = current.left;
                isLeftChild = true;
            } else {
                parent = current;
                current = current.right;
                isLeftChild = false;
            }

        }

        if (nodeToDelete == null) {
            return;
        }

        // Case 1: no children
        if (nodeToDelete.left == null && nodeToDelete.right == null) {
            if (nodeToDelete == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        // Case 2: only 1 child
        else if (nodeToDelete.right == null) {
            if (nodeToDelete == root) {
                root = nodeToDelete.left;
            } else if (isLeftChild) {
                parent.left = nodeToDelete.left;
            } else {
                parent.right = nodeToDelete.left;
            }
        } else if (nodeToDelete.left == null) {
            if (nodeToDelete == root) {
                root = nodeToDelete.right;
            } else if (isLeftChild) {
                parent.left = nodeToDelete.right;
            } else {
                parent.right = nodeToDelete.right;
            }
        }
        // Case 3: 2 children
        else if (nodeToDelete.left != null && nodeToDelete.right != null) {
            Node replacement = findReplacement(nodeToDelete);
            if (nodeToDelete == root) {
                root = replacement;
            } else if (isLeftChild) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
        }

    }

    private Node findReplacement(Node nodeToDelete) {

        Node replacement = nodeToDelete;
        Node parentReplacement = nodeToDelete;
        Node current = nodeToDelete.right;

        while (current != null) {
            parentReplacement = replacement;
            replacement = current;
            current = current.left;
        }

        if (replacement != nodeToDelete.right) {
            parentReplacement.left = replacement.right;
            replacement.right = nodeToDelete.right;
        }

        replacement.left = nodeToDelete.left;

        return replacement;
    }

    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.value);
            traverseInOrder(node.right);
        }
    }

    public void traversePreOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node.value);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    public void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);

            System.out.print(" " + node.value);
        }
    }

    public void traverseLevelOrder() {
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {

            Node node = nodes.remove();

            System.out.print(" " + node.value);

            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.left != null) {
                nodes.add(node.right);
            }
        }
    }

    class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }
}

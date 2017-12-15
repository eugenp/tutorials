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

        addRecursive(root, value);
    }

    private Node addRecursive(Node current, int value) {

        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else {
            current.right = addRecursive(current.right, value);
        }

        return current;

    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean containsNode(int value) {
        return containsNodeRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, int value) {

        if (current == null) {
            return false;
        } else if (value == current.value) {
            return true;
        } else if (value < current.value) {
            return containsNodeRecursive(current.left, value);
        } else {
            return containsNodeRecursive(current.right, value);
        }

    }

    public void delete(int value) {

        NodeVO nodeToDeleteAux = findNodeToDelete(root, root, false, value);

        if (nodeToDeleteAux == null) {
            return;
        }

        // Case 1: no children
        if (nodeToDeleteAux.node.left == null && nodeToDeleteAux.node.right == null) {
            if (nodeToDeleteAux.node == root) {
                root = null;
            } else if (nodeToDeleteAux.isLeftChild) {
                nodeToDeleteAux.parent.left = null;
            } else {
                nodeToDeleteAux.parent.right = null;
            }
        }
        // Case 2: only 1 child
        else if (nodeToDeleteAux.node.right == null) {
            if (nodeToDeleteAux.node == root) {
                root = nodeToDeleteAux.node.left;
            } else if (nodeToDeleteAux.isLeftChild) {
                nodeToDeleteAux.parent.left = nodeToDeleteAux.node.left;
            } else {
                nodeToDeleteAux.parent.right = nodeToDeleteAux.node.left;
            }
        } else if (nodeToDeleteAux.node.left == null) {
            if (nodeToDeleteAux.node == root) {
                root = nodeToDeleteAux.node.right;
            } else if (nodeToDeleteAux.isLeftChild) {
                nodeToDeleteAux.parent.left = nodeToDeleteAux.node.right;
            } else {
                nodeToDeleteAux.parent.right = nodeToDeleteAux.node.right;
            }
        }
        // Case 3: 2 children
        else if (nodeToDeleteAux.node.left != null && nodeToDeleteAux.node.right != null) {
            Node replacement = findReplacement(nodeToDeleteAux.node);
            if (nodeToDeleteAux.node == root) {
                root = replacement;
            } else if (nodeToDeleteAux.isLeftChild) {
                nodeToDeleteAux.parent.left = replacement;
            } else {
                nodeToDeleteAux.parent.right = replacement;
            }
        }

    }

    private NodeVO findNodeToDelete(Node current, Node parent, boolean isLeftChild, int value) {

        if (current == null) {
            return null;
        } else if (value == current.value) {
            return new NodeVO(current, parent, isLeftChild);
        } else if (value < current.value) {
            return findNodeToDelete(current.left, current, true, value);
        } else {
            return findNodeToDelete(current.right, current, false, value);
        }
    }

    private Node findReplacement(Node nodeToDelete) {

        NodeVO replacementNodeVO = findSmallestNode(nodeToDelete, nodeToDelete);

        if (replacementNodeVO.node != nodeToDelete.right) {
            replacementNodeVO.parent.left = replacementNodeVO.node.right;
            replacementNodeVO.node.right = nodeToDelete.right;
        }

        replacementNodeVO.node.left = nodeToDelete.left;

        return replacementNodeVO.node;
    }

    private NodeVO findSmallestNode(Node root, Node parent) {

        if (root.left == null) {
            return new NodeVO(root, parent);
        }

        return findSmallestNode(root.left, root);
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

    class NodeVO {
        Node node;
        Node parent;
        boolean isLeftChild;

        NodeVO(Node node, Node parent) {
            this.node = node;
            this.parent = parent;
        }

        NodeVO(Node node, Node parent, boolean isLeftChild) {
            this.node = node;
            this.parent = parent;
            this.isLeftChild = isLeftChild;
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

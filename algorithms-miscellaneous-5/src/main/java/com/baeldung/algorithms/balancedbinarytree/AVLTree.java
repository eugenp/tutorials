package com.baeldung.algorithms.balancedbinarytree;

public class AVLTree {

    public static class Node {
        int key;
        int height;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;

    public Node find(int key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) {
                return current;
            }
            current = current.key < key ? current.right : current.left;
        }
        return null;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    public Node getRoot() {
        return root;
    }

    public int height(){
        return root == null ? -1: root.height;
    }

    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        } else if (node.key > key) {
            node.left = insert(node.left, key);
        } else if (node.key < key) {
            node.right = insert(node.right, key);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }

    private Node delete(Node node, int key) {
        if (node == null) {
            return node;
        } else if (node.key > key) {
            node.left = delete(node.left, key);
        } else if (node.key < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = delete(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private Node mostLeftChild(Node node)
    {
        Node current = node;
        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;
        return current;
    }

    private Node rebalance(Node n) {
        updateHeight(n);
        int balance = getBalance(n);
        if (balance > 1) {
            if (height(n.right.right) > height(n.right.left)) {
                n = rotateLeft(n);
            } else {
                n.right = rotateRight(n.right);
                n = rotateLeft(n);
            }
        } else if (balance < -1) {
            if (height(n.left.left) > height(n.left.right))
                n = rotateRight(n);
            else {
                n.left = rotateLeft(n.left);
                n = rotateRight(n);
            }
        }
        return n;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node t2 = x.right;
        x.right = y;
        y.left = t2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node t2 = x.left;
        x.left = y;
        y.right = t2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(Node n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }
}

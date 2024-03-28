package com.baeldung.algorithms.skiplist;

class Node {
    int value;
    Node[] forward; // array to hold references to different levels

    public Node(int value, int level) {
        this.value = value;
        this.forward = new Node[level + 1]; // level + 1 because level is 0-based
    }
}

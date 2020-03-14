package com.baeldung.algorithms.boruvka;

import java.util.Iterator;

public class Tree implements Iterable<Edge> {
    private Node root;
    private int edgeCount;

    private static class Node {
        private Edge edge;
        private Node next;

        public String toString() {
            String nextStr = next != null ? next.toString() : "";
            return edge.toString() + " | " + nextStr;
        }
    }

    public Tree() {
        root = null;
        edgeCount = 0;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void addEdge(Edge edge) {
        Node oldRoot = root;
        root = new Node();
        root.edge = edge;
        root.next = oldRoot;
        edgeCount++;
    }

    public String toString() {
        String rootStr = root != null ? root.toString() : "";
        return "Tree: " + rootStr + "Size: " + edgeCount;
    }

    public Iterator<Edge> iterator() {
        return new LinkedIterator(root);
    }

    private class LinkedIterator implements Iterator<Edge> {
        private Node current;

        public LinkedIterator(Node root) {
            current = root;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Edge next() {
            Edge edge = current.edge;
            current = current.next;
            return edge;
        }
    }

}

package com.baeldung.algorithms.boruvka;

import java.util.Arrays;

public class DisjointSet {

    private int[] nodeParents;
    private int[] nodeRanks;

    public DisjointSet(int n) {
        nodeParents = new int[n];
        nodeRanks = new int[n];
        for (int i = 0; i < n; i++) {
            nodeParents[i] = i;
            nodeRanks[i] = 0;
        }
    }

    public int getParent(int node) {
        while (node != nodeParents[node]) {
            node = nodeParents[node];
        }
        return node;
    }

    public void union(int node1, int node2) {
        int node1Parent = getParent(node1);
        int node2Parent = getParent(node2);
        if (node1Parent == node2Parent) {
            return;
        }

        if (nodeRanks[node1Parent] < nodeRanks[node2Parent]) {
            nodeParents[node1Parent] = node2Parent;
        }
        else if (nodeRanks[node1Parent] > nodeRanks[node2Parent]) {
            nodeParents[node2Parent] = node1Parent;
        }
        else {
            nodeParents[node2Parent] = node1Parent;
            nodeRanks[node1Parent]++;
        }
    }

    public String toString() {
        return "Parent: " + Arrays.toString(nodeParents) + "Rank: " + Arrays.toString(nodeRanks);
    }

}

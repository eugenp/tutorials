package com.baeldung.algorithms.boruvka;

public class UnionFind {
    private int[] parents;
    private int[] ranks;

    public UnionFind(int n) {
        parents = new int[n];
        ranks = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
            ranks[i] = 0;
        }
    }

    public int find(int u) {
        while (u != parents[u]) {
            u = parents[u];
        }
        return u;
    }

    public void union(int u, int v) {
        int uParent = find(u);
        int vParent = find(v);
        if (uParent == vParent) {
            return;
        }

        if (ranks[uParent] < ranks[vParent]) {
            parents[uParent] = vParent;
        } else if (ranks[uParent] > ranks[vParent]) {
            parents[vParent] = uParent;
        } else {
            parents[vParent] = uParent;
            ranks[uParent]++;
        }
    }
}

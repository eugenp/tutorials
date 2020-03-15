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

    public int find(int vertex) {
        while (vertex != parents[vertex]) {
            vertex = parents[vertex];
        }
        return vertex;
    }

    public void union(int vertex1, int vertex2) {
        int vertex1Parent = find(vertex1);
        int vertex2Parent = find(vertex2);
        if (vertex1Parent == vertex2Parent) {
            return;
        }

        if (ranks[vertex1Parent] < ranks[vertex2Parent]) {
            parents[vertex1Parent] = vertex2Parent;
        } else if (ranks[vertex1Parent] > ranks[vertex2Parent]) {
            parents[vertex2Parent] = vertex1Parent;
        } else {
            parents[vertex2Parent] = vertex1Parent;
            ranks[vertex1Parent]++;
        }
    }
}

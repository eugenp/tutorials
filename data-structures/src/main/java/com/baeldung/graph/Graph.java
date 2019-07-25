package com.baeldung.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Graph {

    private List<List<Integer>> neighbours;
    private int n;

    public Graph(int n) {
        this.n = n;
        this.neighbours = new ArrayList<List<Integer>>(n);
        for (int i = 0; i < n; i++) {
            this.neighbours.add(new ArrayList<Integer>());
        }
    }

    public void addEdge(int src, int dest) {
        this.neighbours.get(src)
            .add(dest);
    }

    public void dfsWithoutRecursion(int start) {
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] isVisited = new boolean[n];
        stack.push(start);
        while (!stack.isEmpty()) {
            int current = stack.pop();
            isVisited[current] = true;
            System.out.print(" " + current);
            for (int dest : neighbours.get(current)) {
                if (!isVisited[dest])
                    stack.push(dest);
            }
        }
    }

    public void dfs(int start) {
        boolean[] isVisited = new boolean[n];
        dfsRecursive(start, isVisited);
    }

    private void dfsRecursive(int current, boolean[] isVisited) {
        isVisited[current] = true;
        System.out.print(" " + current);
        for (int dest : neighbours.get(current)) {
            if (!isVisited[dest])
                dfsRecursive(dest, isVisited);
        }
    }

    public void topologicalSort(int start) {
        Stack<Integer> result = new Stack<Integer>();
        boolean[] isVisited = new boolean[n];
        topologicalSortRecursive(start, isVisited, result);
        while (!result.isEmpty()) {
            System.out.print(" " + result.pop());
        }
    }

    private void topologicalSortRecursive(int current, boolean[] isVisited, Stack<Integer> result) {
        isVisited[current] = true;
        for (int dest : neighbours.get(current)) {
            if (!isVisited[dest])
                topologicalSortRecursive(dest, isVisited, result);
        }
        result.push(current);
    }
}

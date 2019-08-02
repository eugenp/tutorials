package com.baeldung.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Graph {

    private Map<Integer, List<Integer>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<Integer, List<Integer>>();
    }

    public void addVertex(int vertex) {
        adjVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(int src, int dest) {
        adjVertices.get(src).add(dest);
    }

    public void dfsWithoutRecursion(int start) {
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] isVisited = new boolean[adjVertices.size()];
        stack.push(start);
        while (!stack.isEmpty()) {
            int current = stack.pop();
            isVisited[current] = true;
            System.out.print(" " + current);
            for (int dest : adjVertices.get(current)) {
                if (!isVisited[dest])
                    stack.push(dest);
            }
        }
    }

    public void dfs(int start) {
        boolean[] isVisited = new boolean[adjVertices.size()];
        dfsRecursive(start, isVisited);
    }

    private void dfsRecursive(int current, boolean[] isVisited) {
        isVisited[current] = true;
        System.out.print(" " + current);
        for (int dest : adjVertices.get(current)) {
            if (!isVisited[dest])
                dfsRecursive(dest, isVisited);
        }
    }

    public void topologicalSort(int start) {
        Stack<Integer> result = new Stack<Integer>();
        boolean[] isVisited = new boolean[adjVertices.size()];
        topologicalSortRecursive(start, isVisited, result);
        while (!result.isEmpty()) {
            System.out.print(" " + result.pop());
        }
    }

    private void topologicalSortRecursive(int current, boolean[] isVisited, Stack<Integer> result) {
        isVisited[current] = true;
        for (int dest : adjVertices.get(current)) {
            if (!isVisited[dest])
                topologicalSortRecursive(dest, isVisited, result);
        }
        result.push(current);
    }

}

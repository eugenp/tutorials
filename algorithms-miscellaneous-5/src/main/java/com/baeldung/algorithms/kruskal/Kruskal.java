package com.baeldung.algorithms.kruskal;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Kruskal {

    public ValueGraph<Integer, Double> minSpanningTree(ValueGraph<Integer, Double> graph) {
        
        return spanningTree(graph, true);
    }

    public ValueGraph<Integer, Double> maxSpanningTree(ValueGraph<Integer, Double> graph) {
        return spanningTree(graph, false);
    }

    private ValueGraph<Integer, Double> spanningTree(ValueGraph<Integer, Double> graph, boolean minSpanningTree) {
        Set<EndpointPair<Integer>> edges = graph.edges();
        List<EndpointPair<Integer>> edgeList = new ArrayList<>(edges);

        if (minSpanningTree) {
            edgeList.sort(Comparator.comparing(e -> graph.edgeValue(e).get()));
        } else {
            edgeList.sort(Collections.reverseOrder(Comparator.comparing(e -> graph.edgeValue(e).get())));
        }

        int totalEdges = edgeList.size();
        int totalNodes = graph.nodes().size();
        int edgeCount = 0;
        List<Integer> roots = new ArrayList<>(totalNodes);
        List<Integer> sizes = new ArrayList<>(totalNodes);
        for (int i = 0; i < totalNodes; i++) {
            roots.add(i);
            sizes.add(1);
        }

        MutableValueGraph<Integer, Double> spanningTree = ValueGraphBuilder.undirected().build();
        for (int i = 0; i < totalEdges; i++) {
            EndpointPair<Integer> edge = edgeList.get(i);
            if (detectCycle(edge.nodeU(), edge.nodeV(), roots, sizes)) {
                continue;
            }
            spanningTree.putEdgeValue(edge.nodeU(), edge.nodeV(), graph.edgeValue(edge).get());
            edgeCount++;
            if (edgeCount == totalNodes - 1) {
                break;
            }
        }
        return spanningTree;
    }

    private Integer find(Integer x, List<Integer> roots) {
        Integer root = roots.get(x);
        if (!root.equals(x)) {
            roots.set(x, find(root, roots));
        }
        return roots.get(x);
    }

    private void unionBySize(Integer rootU, Integer rootV, List<Integer> roots, List<Integer> sizes) {
        Integer total = sizes.get(rootU) + sizes.get(rootV);
        if (sizes.get(rootU) < sizes.get(rootV)) {
            roots.set(rootU, rootV);
            sizes.set(rootV, total);
        } else {
            roots.set(rootV, rootU);
            sizes.set(rootU, total);
        }
    }

    private boolean detectCycle(Integer u, Integer v, List<Integer> roots, List<Integer> sizes) {
        Integer rootU = find(u, roots);
        Integer rootV = find(v, roots);
        if (rootU.equals(rootV)) {
            return true;
        }
        unionBySize(rootU, rootV, roots, sizes);
        return false;
    }
}

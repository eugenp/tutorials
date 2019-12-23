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

        int totalNodes = graph.nodes().size();
        CycleDetector cycleDetector = new CycleDetector(totalNodes);
        int edgeCount = 0;

        MutableValueGraph<Integer, Double> spanningTree = ValueGraphBuilder.undirected().build();
        for (EndpointPair<Integer> edge : edgeList) {
            if (cycleDetector.detectCycle(edge.nodeU(), edge.nodeV())) {
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

}

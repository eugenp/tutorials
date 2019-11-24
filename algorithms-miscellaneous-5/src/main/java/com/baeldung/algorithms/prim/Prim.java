package com.baeldung.algorithms.prim;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

public class Prim {

    private List<Vertex> graph;

    public Prim(List<Vertex> graph) {
        this.graph = graph;
    }

    public void run() {
        if (graph.size() > 0) {
            graph.get(0)
                .setVisited(true);
        }
        while (isDisconnected()) {
            Edge nextMinimum = new Edge(Integer.MAX_VALUE);
            Vertex nextVertex = graph.get(0);
            for (Vertex vertex : graph) {
                if (vertex.isVisited()) {
                    Pair<Vertex, Edge> candidate = vertex.nextMinimum();
                    if (candidate.getValue()
                        .getWeight() < nextMinimum.getWeight()) {
                        nextMinimum = candidate.getValue();
                        nextVertex = candidate.getKey();
                    }
                }
            }
            nextMinimum.setIncluded(true);
            nextVertex.setVisited(true);
        }
    }

    private boolean isDisconnected() {
        for (Vertex vertex : graph) {
            if (!vertex.isVisited()) {
                return true;
            }
        }
        return false;
    }

    public String originalGraphToString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : graph) {
            sb.append(vertex.originalToString());
        }
        return sb.toString();
    }

    public void resetPrintHistory() {
        for (Vertex vertex : graph) {
            Iterator it = vertex.getEdges()
                .entrySet()
                .iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                ((Edge) pair.getValue()).setPrinted(false);
            }
        }
    }

    public String minimumSpanningTreeToString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : graph) {
            sb.append(vertex.includedToString());
        }
        return sb.toString();
    }

}

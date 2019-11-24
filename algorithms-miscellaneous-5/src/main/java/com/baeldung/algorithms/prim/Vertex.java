package com.baeldung.algorithms.prim;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

public class Vertex {

    private String label = null;
    private Map<Vertex, Edge> edges = new HashMap<>();
    private boolean isVisited = false;

    public Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<Vertex, Edge> getEdges() {
        return edges;
    }

    public void addEdge(Vertex vertex, Edge edge) {
        this.edges.put(vertex, edge);
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public Pair<Vertex, Edge> nextMinimum() {
        Edge nextMinimum = new Edge(Integer.MAX_VALUE);
        Vertex nextVertex = this;
        Iterator it = edges.entrySet()
            .iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (!((Vertex) pair.getKey()).isVisited()) {
                if (!((Edge) pair.getValue()).isIncluded()) {
                    if (((Edge) pair.getValue()).getWeight() < nextMinimum.getWeight()) {
                        nextMinimum = (Edge) pair.getValue();
                        nextVertex = (Vertex) pair.getKey();
                    }
                }
            }
        }
        return new Pair<>(nextVertex, nextMinimum);
    }

    public String originalToString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = edges.entrySet()
            .iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (!((Edge) pair.getValue()).isPrinted()) {
                sb.append(getLabel());
                sb.append(" --- ");
                sb.append(((Edge) pair.getValue()).getWeight());
                sb.append(" --- ");
                sb.append(((Vertex) pair.getKey()).getLabel());
                sb.append("\n");
                ((Edge) pair.getValue()).setPrinted(true);
            }
        }
        return sb.toString();
    }

    public String includedToString() {
        StringBuilder sb = new StringBuilder();
        if (isVisited()) {
            Iterator it = edges.entrySet()
                .iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if (((Edge) pair.getValue()).isIncluded()) {
                    if (!((Edge) pair.getValue()).isPrinted()) {
                        sb.append(getLabel());
                        sb.append(" --- ");
                        sb.append(((Edge) pair.getValue()).getWeight());
                        sb.append(" --- ");
                        sb.append(((Vertex) pair.getKey()).getLabel());
                        sb.append("\n");
                        ((Edge) pair.getValue()).setPrinted(true);
                    }
                }
            }
        }
        return sb.toString();
    }
}

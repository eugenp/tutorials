package com.baeldung.algorithms.graphcycledetection.domain;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Vertex> vertices;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public Graph(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public void addEdge(Vertex from, Vertex to) {
        from.addNeighbour(to);
    }

    public boolean hasCycle() {
        for (Vertex vertex : vertices) {
            if (!vertex.isVisited() && hasCycle(vertex)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCycle(Vertex sourceVertex) {
        sourceVertex.setBeingVisited(true);

        for (Vertex neighbour : sourceVertex.getAdjacencyList()) {
            if (neighbour.isBeingVisited()) {
                // backward edge exists
                return true;
            } else if (!neighbour.isVisited() && hasCycle(neighbour)) {
                return true;
            }
        }

        sourceVertex.setBeingVisited(false);
        sourceVertex.setVisited(true);
        return false;
    }
}

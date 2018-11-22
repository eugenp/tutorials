package com.baeldung.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    private Set<Vertex> vertices;
    private Set<Edge> edges;
    private Map<Vertex, Set<Edge>> adjVertices;

    Graph() {
        this.vertices = new HashSet<Vertex>();
        this.edges = new HashSet<Edge>();
        this.adjVertices = new HashMap<Vertex, Set<Edge>>();
    }

    boolean addVertex(String label) {
        return vertices.add(new Vertex(label));
    }

    boolean removeVertex(String label) {
        return vertices.remove(new Vertex(label));
    }

    boolean addEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        Edge e = new Edge(v1, v2);
        if (this.edges.add(e)) {
            adjVertices.putIfAbsent(v1, new HashSet<>());
            adjVertices.putIfAbsent(v2, new HashSet<>());
            adjVertices.get(v1)
                .add(e);
            adjVertices.get(v2)
                .add(e);
        }
        return true;
    }

    boolean removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        Edge e = new Edge(v1, v2);
        if (this.edges.remove(e)) {
            Set<Edge> eV1 = adjVertices.get(v1);
            Set<Edge> eV2 = adjVertices.get(v2);

            if (eV1 != null)
                eV1.remove(e);
            if (eV2 != null)
                eV2.remove(e);
        }
        return true;
    }

    Set<Vertex> getAdjVertices(String label) {
        Vertex v = new Vertex(label);
        return adjVertices.get(v)
            .stream()
            .map(e -> e.v1.equals(v) ? e.v2 : e.v1)
            .collect(Collectors.toSet());
    }

    class Vertex {
        String label;
        Vertex(String label) {
            this.label = label;
        }
        @Override
        public boolean equals(Object obj) {
            Vertex vertex = (Vertex) obj;
            return vertex.label == label;
        }
        @Override
        public int hashCode() {
            return label.hashCode();
        }
        @Override
        public String toString() {
            return label;
        }
    }

    class Edge {
        Vertex v1;
        Vertex v2;
        Edge(String label1, String label2) {
            this.v1 = new Vertex(label1);
            this.v2 = new Vertex(label2);
        }
        Edge(Vertex vertex1, Vertex vertex2) {
            this.v1 = vertex1;
            this.v2 = vertex2;
        }
        @Override
        public boolean equals(Object obj) {
            Edge edge = (Edge) obj;
            return edge.v1.equals(v1) && edge.v2.equals(v2);
        }
        @Override
        public int hashCode() {
            return v1.hashCode() + v2.hashCode();
        }
        @Override
        public String toString() {
            return v1.label + "-" + v2.label;
        }
    }
}
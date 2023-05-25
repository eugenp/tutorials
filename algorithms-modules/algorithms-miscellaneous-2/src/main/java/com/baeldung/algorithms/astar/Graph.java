package com.baeldung.algorithms.astar;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Graph<T extends GraphNode> {
    private final Set<T> nodes;

    private final Map<String, Set<String>> connections;

    public Graph(Set<T> nodes, Map<String, Set<String>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    public T getNode(String id) {
        return nodes.stream()
            .filter(node -> node.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
    }

    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
            .map(this::getNode)
            .collect(Collectors.toSet());
    }
}

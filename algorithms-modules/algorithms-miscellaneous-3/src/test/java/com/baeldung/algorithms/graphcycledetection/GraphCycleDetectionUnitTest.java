package com.baeldung.algorithms.graphcycledetection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.graphcycledetection.domain.Graph;
import com.baeldung.algorithms.graphcycledetection.domain.Vertex;

class GraphCycleDetectionUnitTest {

    @Test
    public void givenGraph_whenCycleExists_thenReturnTrue() {

        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");
        Vertex vertexC = new Vertex("C");
        Vertex vertexD = new Vertex("D");

        Graph graph = new Graph();
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);
        graph.addEdge(vertexC, vertexA);
        graph.addEdge(vertexD, vertexC);

        assertTrue(graph.hasCycle());
    }

    @Test
    void givenGraph_whenNoCycleExists_thenReturnFalse() {

        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");
        Vertex vertexC = new Vertex("C");
        Vertex vertexD = new Vertex("D");

        Graph graph = new Graph();
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);
        graph.addEdge(vertexA, vertexC);
        graph.addEdge(vertexD, vertexC);

        assertFalse(graph.hasCycle());
    }
}

package com.baeldung.algorithms.kruskal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class KruskalUnitTest {

    private MutableValueGraph<Integer, Double> graph;

    @Before
    public void setup() {
        graph = ValueGraphBuilder.undirected().build();
        graph.putEdgeValue(0, 1, 8.0);
        graph.putEdgeValue(0, 2, 5.0);
        graph.putEdgeValue(1, 2, 9.0);
        graph.putEdgeValue(1, 3, 11.0);
        graph.putEdgeValue(2, 3, 15.0);
        graph.putEdgeValue(2, 4, 10.0);
        graph.putEdgeValue(3, 4, 7.0);
    }

    @Test
    public void givenGraph_whenMinimumSpanningTree_thenOutputCorrectResult() {
        final Kruskal kruskal = new Kruskal();
        ValueGraph<Integer, Double> spanningTree = kruskal.minSpanningTree(graph);

        assertTrue(spanningTree.hasEdgeConnecting(0, 1));
        assertTrue(spanningTree.hasEdgeConnecting(0, 2));
        assertTrue(spanningTree.hasEdgeConnecting(2, 4));
        assertTrue(spanningTree.hasEdgeConnecting(3, 4));
        assertEquals(graph.edgeValue(0, 1), spanningTree.edgeValue(0, 1));
        assertEquals(graph.edgeValue(0, 2), spanningTree.edgeValue(0, 2));
        assertEquals(graph.edgeValue(2, 4), spanningTree.edgeValue(2, 4));
        assertEquals(graph.edgeValue(3, 4), spanningTree.edgeValue(3, 4));

        assertFalse(spanningTree.hasEdgeConnecting(1, 2));
        assertFalse(spanningTree.hasEdgeConnecting(1, 3));
        assertFalse(spanningTree.hasEdgeConnecting(2, 3));
    }

    @Test
    public void givenGraph_whenMaximumSpanningTree_thenOutputCorrectResult() {
        final Kruskal kruskal = new Kruskal();
        ValueGraph<Integer, Double> spanningTree = kruskal.maxSpanningTree(graph);

        assertTrue(spanningTree.hasEdgeConnecting(0, 1));
        assertTrue(spanningTree.hasEdgeConnecting(1, 3));
        assertTrue(spanningTree.hasEdgeConnecting(2, 3));
        assertTrue(spanningTree.hasEdgeConnecting(2, 4));
        assertEquals(graph.edgeValue(0, 1), spanningTree.edgeValue(0, 1));
        assertEquals(graph.edgeValue(1, 3), spanningTree.edgeValue(1, 3));
        assertEquals(graph.edgeValue(2, 3), spanningTree.edgeValue(2, 3));
        assertEquals(graph.edgeValue(2, 4), spanningTree.edgeValue(2, 4));

        assertFalse(spanningTree.hasEdgeConnecting(0, 2));
        assertFalse(spanningTree.hasEdgeConnecting(1, 2));
        assertFalse(spanningTree.hasEdgeConnecting(3, 4));
    }
}

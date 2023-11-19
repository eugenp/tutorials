package com.baeldung.algorithms.dfs;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GraphUnitTest {

    @Test
    void givenDirectedGraph_whenDFS_thenPrintAllValues() {
        Graph graph = createDirectedGraph();
        boolean[] visited;
        visited = graph.dfs(0);
        boolean[] expected = new boolean[]{true, true, true, true, true, true};
        Assert.assertArrayEquals(expected, visited);
        visited = graph.dfsWithoutRecursion(0);
        Assert.assertArrayEquals(expected, visited);
    }

    @Test
    void givenDirectedGraph_whenGetTopologicalSort_thenPrintValuesSorted() {
        Graph graph = createDirectedGraph();
        List<Integer> list = graph.topologicalSort(0);
        System.out.println(list);
        List<Integer> expected = Arrays.asList(0, 2, 1, 3, 4, 5);
        Assert.assertEquals(expected, list);
    }

    private Graph createDirectedGraph() {
        Graph graph = new Graph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        return graph;
    }
}

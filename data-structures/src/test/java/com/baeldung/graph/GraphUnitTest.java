package com.baeldung.graph;

import org.junit.Test;

public class GraphUnitTest {

    @Test
    public void givenDirectedGraph_whenDFS_thenPrintAllValues() {
        Graph graph = createDirectedGraph();
        graph.dfs(0);
        System.out.println();
        graph.dfsWithoutRecursion(0);
    }

    @Test
    public void givenDirectedGraph_whenGetTopologicalSort_thenPrintValuesSorted() {
        Graph graph = createDirectedGraph();
        graph.topologicalSort(0);
    }

    private Graph createDirectedGraph() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        return graph;
    }
}

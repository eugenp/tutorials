package com.baeldung.algorithms.dfs;

import java.util.List;

import com.baeldung.algorithms.dfs.Graph;
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
        List<Integer> list = graph.topologicalSort(0);
        System.out.println(list);
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

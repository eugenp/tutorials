package com.baeldung.graph;

import org.junit.Assert;
import org.junit.Test;

public class GraphTraversalUnitTest {
    @Test
    public void givenAGraph_whenTraversingDepthFirst_thenExpectedResult() {
        Graph graph = createGraph();
        Assert.assertEquals("[A, D, E, B, C]", 
            GraphTraversal.depthFirstTraversal(graph, "A").toString());
    }
    
    @Test
    public void givenAGraph_whenTraversingBreadthFirst_thenExpectedResult() {
        Graph graph = createGraph();
        Assert.assertEquals("[A, B, D, C, E]", 
            GraphTraversal.breadthFirstTraversal(graph, "A").toString());
    }
    
    Graph createGraph() {
        Graph graph = new Graph();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addEdge("A", "B");
        graph.addEdge("A", "D");
        graph.addEdge("B", "C");
        graph.addEdge("D", "C");
        graph.addEdge("B", "E");
        graph.addEdge("D", "E"); 
        return graph;
    }
}
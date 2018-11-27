package com.baeldung.graph;

import org.junit.Assert;
import org.junit.Test;

public class GraphTraversalUnitTest {
    @Test
    public void givenAGraph_whenTraversingDepthFirst_thenExpectedResult() {
        Graph graph = createGraph();
        Assert.assertEquals("[Bob, Rob, Maria, Alice, Mark]", 
            GraphTraversal.depthFirstTraversal(graph, "Bob").toString());
    }
    
    @Test
    public void givenAGraph_whenTraversingBreadthFirst_thenExpectedResult() {
        Graph graph = createGraph();
        Assert.assertEquals("[Bob, Alice, Rob, Mark, Maria]", 
            GraphTraversal.breadthFirstTraversal(graph, "Bob").toString());
    }
    
    Graph createGraph() {
        Graph graph = new Graph();
        graph.addVertex("Bob");
        graph.addVertex("Alice");
        graph.addVertex("Mark");
        graph.addVertex("Rob");
        graph.addVertex("Maria");
        graph.addEdge("Bob", "Alice");
        graph.addEdge("Bob", "Rob");
        graph.addEdge("Alice", "Mark");
        graph.addEdge("Rob", "Mark");
        graph.addEdge("Alice", "Maria");
        graph.addEdge("Rob", "Maria");
        return graph;
    }
}
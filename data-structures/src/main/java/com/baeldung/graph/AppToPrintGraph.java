package com.baeldung.graph;

import java.util.Set;

public class AppToPrintGraph {

  static Graph createGraph() {
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
   
   public static void main(String[] args) {
    Graph graph = createGraph();
    Set<String> result = GraphTraversal.breadthFirstTraversal(graph, "Bob");
       
    System.out.println(result.toString());   
}
}

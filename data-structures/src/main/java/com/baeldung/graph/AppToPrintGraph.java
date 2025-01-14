package com.baeldung.graph;

import java.util.Set;

public class AppToPrintGraph {
    
   
   public static void main(String[] args) {
    Graph graph = createGraph();
    Set<String> result = GraphTraversal.breadthFirstTraversal(graph, "Bob");
}
}

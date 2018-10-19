package com.baeldung.algorithms;

import org.junit.Test;

import com.baeldung.algorithms.ga.dijkstra.Dijkstra;
import com.baeldung.algorithms.ga.dijkstra.Graph;
import com.baeldung.algorithms.ga.dijkstra.Node;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DijkstraAlgorithmLongRunningUnitTest {

    @Test
    public void whenSPPSolved_thenCorrect() {

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 10);
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);

        List<Node> shortestPathForNodeB = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeC = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeD = Arrays.asList(nodeA, nodeB);
        List<Node> shortestPathForNodeE = Arrays.asList(nodeA, nodeB, nodeD);
        List<Node> shortestPathForNodeF = Arrays.asList(nodeA, nodeB, nodeD);

        for (Node node : graph.getNodes()) {
            switch (node.getName()) {
            case "B":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeB));
                break;
            case "C":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeC));
                break;
            case "D":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeD));
                break;
            case "E":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeE));
                break;
            case "F":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeF));
                break;
            }
        }
    }
}

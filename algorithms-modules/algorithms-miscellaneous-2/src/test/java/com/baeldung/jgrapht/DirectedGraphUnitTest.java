package com.baeldung.jgrapht;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DirectedGraphUnitTest {
    DirectedGraph<String, DefaultEdge> directedGraph;

    @BeforeEach
    public void createDirectedGraph() {
        directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        IntStream.range(1, 10).forEach(i -> {
            directedGraph.addVertex("v" + i);
        });
        directedGraph.addEdge("v1", "v2");
        directedGraph.addEdge("v2", "v4");
        directedGraph.addEdge("v4", "v3");
        directedGraph.addEdge("v3", "v1");
        directedGraph.addEdge("v5", "v4");
        directedGraph.addEdge("v5", "v6");
        directedGraph.addEdge("v6", "v7");
        directedGraph.addEdge("v7", "v5");
        directedGraph.addEdge("v8", "v5");
        directedGraph.addEdge("v9", "v8");
    }

    @Test
    void givenDirectedGraph_whenGetStronglyConnectedSubgraphs_thenPathExistsBetweenStronglyconnectedVertices() {
        StrongConnectivityAlgorithm<String, DefaultEdge> scAlg = new KosarajuStrongConnectivityInspector<>(directedGraph);
        List<DirectedSubgraph<String, DefaultEdge>> stronglyConnectedSubgraphs = scAlg.stronglyConnectedSubgraphs();
        List<String> stronglyConnectedVertices = new ArrayList<>(stronglyConnectedSubgraphs.get(3).vertexSet());

        String randomVertex1 = stronglyConnectedVertices.get(0);
        String randomVertex2 = stronglyConnectedVertices.get(3);
        AllDirectedPaths<String, DefaultEdge> allDirectedPaths = new AllDirectedPaths<>(directedGraph);

        List<GraphPath<String, DefaultEdge>> possiblePathList = allDirectedPaths.getAllPaths(randomVertex1, randomVertex2, false, stronglyConnectedVertices.size());
        assertTrue(possiblePathList.size() > 0);
    }

    @Test
    void givenDirectedGraphWithCycle_whenCheckCycles_thenDetectCycles() {
        CycleDetector<String, DefaultEdge> cycleDetector = new CycleDetector<String, DefaultEdge>(directedGraph);
        assertTrue(cycleDetector.detectCycles());
        Set<String> cycleVertices = cycleDetector.findCycles();
        assertTrue(cycleVertices.size() > 0);
    }

    @Test
    void givenDirectedGraph_whenCreateInstanceDepthFirstIterator_thenGetIterator() {
        DepthFirstIterator depthFirstIterator = new DepthFirstIterator<>(directedGraph);
        assertNotNull(depthFirstIterator);
    }

    @Test
    void givenDirectedGraph_whenCreateInstanceBreadthFirstIterator_thenGetIterator() {
        BreadthFirstIterator breadthFirstIterator = new BreadthFirstIterator<>(directedGraph);
        assertNotNull(breadthFirstIterator);
    }

    @Test
    void givenDirectedGraph_whenGetDijkstraShortestPath_thenGetNotNullPath() {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(directedGraph);
        List<String> shortestPath = dijkstraShortestPath.getPath("v1", "v4").getVertexList();
        assertNotNull(shortestPath);
    }

    @Test
    void givenDirectedGraph_whenGetBellmanFordShortestPath_thenGetNotNullPath() {
        BellmanFordShortestPath bellmanFordShortestPath = new BellmanFordShortestPath(directedGraph);
        List<String> shortestPath = bellmanFordShortestPath.getPath("v1", "v4").getVertexList();
        assertNotNull(shortestPath);
    }
}

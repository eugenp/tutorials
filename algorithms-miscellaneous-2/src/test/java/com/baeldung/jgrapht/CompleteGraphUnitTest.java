package com.baeldung.jgrapht;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jgrapht.VertexFactory;
import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;

public class CompleteGraphUnitTest {

    static SimpleWeightedGraph<String, DefaultEdge> completeGraph;
    static int size = 10;

    @Before
    public void createCompleteGraph() {
        completeGraph = new SimpleWeightedGraph<>(DefaultEdge.class);
        CompleteGraphGenerator<String, DefaultEdge> completeGenerator = new CompleteGraphGenerator<String, DefaultEdge>(size);
        VertexFactory<String> vFactory = new VertexFactory<String>() {
            private int id = 0;
            public String createVertex() {
                return "v" + id++;
            }
        };
        completeGenerator.generateGraph(completeGraph, vFactory, null);
    }

    @Test
    public void givenCompleteGraph_whenGetHamiltonianCyclePath_thenGetVerticeListInSequence() {
        List<String> verticeList = HamiltonianCycle.getApproximateOptimalForCompleteGraph(completeGraph);
        assertEquals(verticeList.size(), completeGraph.vertexSet().size());
    }
}

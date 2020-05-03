package com.baeldung.algorithms.boruvka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class BoruvkaUnitTest {

    private MutableValueGraph<Integer, Integer> graph;

    @Before
    public void setup() {
        graph = ValueGraphBuilder.undirected()
            .build();
        graph.putEdgeValue(0, 1, 8);
        graph.putEdgeValue(0, 2, 5);
        graph.putEdgeValue(1, 2, 9);
        graph.putEdgeValue(1, 3, 11);
        graph.putEdgeValue(2, 3, 15);
        graph.putEdgeValue(2, 4, 10);
        graph.putEdgeValue(3, 4, 7);
    }

    @Test
    public void givenInputGraph_whenBoruvkaPerformed_thenMinimumSpanningTree() {
        BoruvkaMST boruvkaMST = new BoruvkaMST(graph);
        MutableValueGraph<Integer, Integer> mst = boruvkaMST.getMST();

        assertEquals(30, boruvkaMST.getTotalWeight());
        assertEquals(4, mst.edges().size());
    }

}

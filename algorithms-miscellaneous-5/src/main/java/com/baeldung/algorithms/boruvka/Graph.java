package com.baeldung.algorithms.boruvka;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Graph {

    private int nodes;
    private int edges;
    private Tree[] trees;

    public Graph(Input jsonGraph) throws JsonParseException, JsonMappingException, IOException {
        nodes = jsonGraph.getNodes();
        trees = (Tree[]) new Tree[nodes];
        for (int i = 0; i < nodes; i++) {
            trees[i] = new Tree();
        }

        int edgesFromInput = jsonGraph.getEdges();
        for (int i = 0; i < edgesFromInput; i++) {
            int first = jsonGraph.getEdgeList()
                .get(i)
                .getFirst();
            int second = jsonGraph.getEdgeList()
                .get(i)
                .getSecond();
            int weight = jsonGraph.getEdgeList()
                .get(i)
                .getWeight();
            Edge edge = new Edge(first, second, weight);
            
            trees[first].addEdge(edge);
            trees[second].addEdge(edge);
            edges++;
        }

    }

    public int getNodes() {
        return nodes;
    }

    public int getEdges() {
        return edges;
    }

    public Iterable<Edge> iterableTree(int i) {
        return trees[i];
    }

    public Iterable<Edge> getAllEdges() {
        Iterable<Edge> list = new Tree();
        for (int i = 0; i < nodes; i++) {
            for (Edge edge : iterableTree(i)) {
                if (edge.getOtherNode(i) > i) {
                    ((Tree) list).addEdge(edge);
                }
            }
        }
        return list;
    }
}

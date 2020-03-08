package com.baeldung.algorithms.boruvka;

import java.util.List;

public class Input {
    private int nodes;
    private int edges;
    private List<E> edgeList;

    public int getNodes() {
        return nodes;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    public int getEdges() {
        return edges;
    }

    public void setEdges(int edges) {
        this.edges = edges;
    }

    public List<E> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<E> edgeList) {
        this.edgeList = edgeList;
    }

    static class E {
        private int first;
        private int second;
        private int weight;

        public int getFirst() {
            return first;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

    }

}

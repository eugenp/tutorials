package com.baeldung.algorithms.boruvka;

public class Edge {

    private final int first;
    private final int second;
    private final int weight;

    public Edge(int first, int second, int weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int getFirst() {
        return first;
    }
    
    public int getSecond() {
        return second;
    }

    public int getOtherNode(int firstNode) {
        int secondNode = 0;
        if (firstNode == first)
            secondNode = second;
        else if (firstNode == second)
            secondNode = first;
        return secondNode;
    }

    public String toString() {
        return String.format("%d-%d %d", first, second, weight);
    }

}

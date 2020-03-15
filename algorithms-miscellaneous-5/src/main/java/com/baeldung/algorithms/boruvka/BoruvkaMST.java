package com.baeldung.algorithms.boruvka;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class BoruvkaMST {

    private static MutableValueGraph<Integer, Integer> mst = ValueGraphBuilder.undirected()
        .build();
    private static int totalWeight;

    public BoruvkaMST(MutableValueGraph<Integer, Integer> graph) {

        int size = graph.nodes()
            .size();

        UnionFind uf = new UnionFind(size);

        for (int t = 1; t < size && mst.edges().size() < size - 1; t = t + t) {
            EndpointPair<Integer>[] closestEdgeArray = new EndpointPair[size];
            for (EndpointPair<Integer> edge : graph.edges()) {
                int first = edge.nodeU();
                int second = edge.nodeV();
                int firstParent = uf.find(first);
                int secondParent = uf.find(second);
                if (firstParent == secondParent) {
                    continue;
                }

                int weight = graph.edgeValueOrDefault(first, second, 0);

                if (closestEdgeArray[firstParent] == null) {
                    closestEdgeArray[firstParent] = edge;
                }
                if (closestEdgeArray[secondParent] == null) {
                    closestEdgeArray[secondParent] = edge;
                }

                int firstParentWeight = graph.edgeValueOrDefault(closestEdgeArray[firstParent].nodeU(), closestEdgeArray[firstParent].nodeV(), 0);
                int secondParentWeight = graph.edgeValueOrDefault(closestEdgeArray[secondParent].nodeU(), closestEdgeArray[secondParent].nodeV(), 0);

                if (weight < firstParentWeight) {
                    closestEdgeArray[firstParent] = edge;
                }
                if (weight < secondParentWeight) {
                    closestEdgeArray[secondParent] = edge;
                }
            }

            for (int i = 0; i < size; i++) {
                EndpointPair<Integer> edge = closestEdgeArray[i];
                if (edge != null) {
                    int first = edge.nodeU();
                    int second = edge.nodeV();
                    int weight = graph.edgeValueOrDefault(first, second, 0);
                    if (uf.find(first) != uf.find(second)) {
                        mst.putEdgeValue(first, second, weight);
                        totalWeight += weight;
                        uf.union(first, second);
                    }
                }
            }
        }
    }

    public MutableValueGraph<Integer, Integer> getMST() {
        return mst;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public String toString() {
        return "MST: " + mst.toString() + " | Total Weight: " + totalWeight;
    }

}

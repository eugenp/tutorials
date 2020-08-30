package com.baeldung.algorithms.boruvka;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class BoruvkaMST {

    private static MutableValueGraph<Integer, Integer> mst = ValueGraphBuilder.undirected()
        .build();
    private static int totalWeight;

    public BoruvkaMST(MutableValueGraph<Integer, Integer> graph) {

        int size = graph.nodes().size();

        UnionFind uf = new UnionFind(size);

        // repeat at most log N times or until we have N-1 edges
        for (int t = 1; t < size && mst.edges().size() < size - 1; t = t + t) {
            
            EndpointPair<Integer>[] closestEdgeArray = new EndpointPair[size];

            // foreach tree in graph, find closest edge
            for (EndpointPair<Integer> edge : graph.edges()) {
                int u = edge.nodeU();
                int v = edge.nodeV();
                int uParent = uf.find(u);
                int vParent = uf.find(v);
                if (uParent == vParent) {
                    continue; // same tree
                }

                int weight = graph.edgeValueOrDefault(u, v, 0);

                if (closestEdgeArray[uParent] == null) {
                    closestEdgeArray[uParent] = edge;
                }
                if (closestEdgeArray[vParent] == null) {
                    closestEdgeArray[vParent] = edge;
                }

                int uParentWeight = graph.edgeValueOrDefault(closestEdgeArray[uParent].nodeU(), closestEdgeArray[uParent].nodeV(), 0);
                int vParentWeight = graph.edgeValueOrDefault(closestEdgeArray[vParent].nodeU(), closestEdgeArray[vParent].nodeV(), 0);

                if (weight < uParentWeight) {
                    closestEdgeArray[uParent] = edge;
                }
                if (weight < vParentWeight) {
                    closestEdgeArray[vParent] = edge;
                }
            }

            // add newly discovered edges to MST
            for (int i = 0; i < size; i++) {
                EndpointPair<Integer> edge = closestEdgeArray[i];
                if (edge != null) {
                    int u = edge.nodeU();
                    int v = edge.nodeV();
                    int weight = graph.edgeValueOrDefault(u, v, 0);
                    // don't add the same edge twice
                    if (uf.find(u) != uf.find(v)) {
                        mst.putEdgeValue(u, v, weight);
                        totalWeight += weight;
                        uf.union(u, v);
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

package com.baeldung.algorithms.boruvka;

public class BoruvkaMST {

    private static Tree mst = new Tree();
    private static int totalWeight;

    public BoruvkaMST(Graph graph) {
        DisjointSet dSet = new DisjointSet(graph.getNodes());

        // repeat at most log N times or until we have N-1 edges
        for (int t = 1; t < graph.getNodes() && mst.getEdgeCount() < graph.getNodes() - 1; t = t + t) {

            // foreach tree in forest, find closest edge
            Edge[] closestEdgeArray = new Edge[graph.getNodes()];
            for (Edge edge : graph.getAllEdges()) {
                int first = edge.getFirst();
                int second = edge.getSecond();
                int firstParent = dSet.getParent(first);
                int secondParent = dSet.getParent(second);
                if (firstParent == secondParent) {
                    continue; // same tree
                }
                if (closestEdgeArray[firstParent] == null || edge.getWeight() < closestEdgeArray[firstParent].getWeight()) {
                    closestEdgeArray[firstParent] = edge;
                }
                if (closestEdgeArray[secondParent] == null || edge.getWeight() < closestEdgeArray[secondParent].getWeight()) {
                    closestEdgeArray[secondParent] = edge;
                }
            }

            // add newly discovered edges to MST
            for (int i = 0; i < graph.getNodes(); i++) {
                Edge edge = closestEdgeArray[i];
                if (edge != null) {
                    int first = edge.getFirst();
                    int second = edge.getSecond();
                    // don't add the same edge twice
                    if (dSet.getParent(first) != dSet.getParent(second)) {
                        mst.addEdge(edge);
                        totalWeight += edge.getWeight();
                        dSet.union(first, second);
                    }
                }
            }
        }
    }

    public Iterable<Edge> getMST() {
        return mst;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public String toString() {
        return "MST: " + mst.toString() + " | Total Weight: " + totalWeight;
    }

}

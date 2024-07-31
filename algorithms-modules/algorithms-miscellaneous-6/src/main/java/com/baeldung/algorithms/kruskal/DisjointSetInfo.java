package com.baeldung.algorithms.kruskal;

public class DisjointSetInfo {

    private Integer parentNode;
    private int rank;

    DisjointSetInfo(Integer nodeNumber) {
        setParentNode(nodeNumber);
        setRank(1);
    }

    public Integer getParentNode() {
        return parentNode;
    }

    public void setParentNode(Integer parentNode) {
        this.parentNode = parentNode;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

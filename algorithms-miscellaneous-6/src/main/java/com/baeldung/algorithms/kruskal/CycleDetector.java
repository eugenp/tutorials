package com.baeldung.algorithms.kruskal;

import java.util.ArrayList;
import java.util.List;

public class CycleDetector {

    List<DisjointSetInfo> nodes;

    public CycleDetector(int totalNodes) {
        initDisjointSets(totalNodes);
    }

    public boolean detectCycle(Integer u, Integer v) {
        Integer rootU = pathCompressionFind(u);
        Integer rootV = pathCompressionFind(v);
        if (rootU.equals(rootV)) {
            return true;
        }
        unionByRank(rootU, rootV);
        return false;
    }

    private void initDisjointSets(int totalNodes) {
        nodes = new ArrayList<>(totalNodes);
        for (int i = 0; i < totalNodes; i++) {
            nodes.add(new DisjointSetInfo(i));
        }
    }

    private Integer find(Integer node) {
        Integer parent = nodes.get(node).getParentNode();
        if (parent.equals(node)) {
            return node;
        } else {
            return find(parent);
        }
    }

    private Integer pathCompressionFind(Integer node) {
        DisjointSetInfo setInfo = nodes.get(node);
        Integer parent = setInfo.getParentNode();
        if (parent.equals(node)) {
            return node;
        } else {
            Integer parentNode = find(parent);
            setInfo.setParentNode(parentNode);
            return parentNode;
        }
    }

    private void union(Integer rootU, Integer rootV) {
        DisjointSetInfo setInfoU = nodes.get(rootU);
        setInfoU.setParentNode(rootV);
    }

    private void unionByRank(int rootU, int rootV) {
        DisjointSetInfo setInfoU = nodes.get(rootU);
        DisjointSetInfo setInfoV = nodes.get(rootV);
        int rankU = setInfoU.getRank();
        int rankV = setInfoV.getRank();
        if (rankU < rankV) {
            setInfoU.setParentNode(rootV);
        } else {
            setInfoV.setParentNode(rootU);
            if (rankU == rankV) {
                setInfoU.setRank(rankU + 1);
            }
        }
    }

}

package com.baeldung.algorithms.mcts.montecarlo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.baeldung.algorithms.mcts.tree.Node;

public class UCT {
    final static double C = 1.41;

    public static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0)
            return Integer.MAX_VALUE;
        return ((double) nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }

    public static Node findBestNodeWithUCT(Node node) {
        int parentVisit = node.getState().getVisitCount();
        List<Node> childNodes = node.getChildArray();
        return Collections.max(childNodes, Comparator.comparing(c -> {
            double score = uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount());
            return score;
        }));
    }
}

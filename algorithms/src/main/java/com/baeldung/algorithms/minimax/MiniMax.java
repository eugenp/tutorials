package com.baeldung.algorithms.minimax;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MiniMax {
    private Tree tree;

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public void constructTree(int noOfBones) {
        tree = new Tree();
        Node root = new Node(noOfBones, true);
        tree.setRoot(root);
        constructTree(root);
    }

    private void constructTree(Node node) {
        List<Integer> listofPossibleHeaps = GameOfBones.getPossibleStates(node.getNoOfBones());
        boolean isMaxPlayer = !node.isMaxPlayer();
        listofPossibleHeaps.forEach(n -> {
            Node newNode = new Node(n, isMaxPlayer);
            node.addChild(newNode);
            if (newNode.getNoOfBones() > 0) {
                constructTree(newNode);
            }
        });
    }

    public boolean checkWin() {
        Node root = tree.getRoot();
        checkWin(root);
        return root.getScore() == 1 ? true : false;
    }

    private void checkWin(Node node) {
        List<Node> children = node.getChildren();
        boolean isMaxPlayer = node.isMaxPlayer();
        children.forEach(child -> {
            if (child.getNoOfBones() == 0) {
                if (isMaxPlayer) {
                    child.setScore(1);
                } else {
                    child.setScore(-1);
                }
            } else {
                checkWin(child);
            }
        });
        Node bestChild = findBestChild(isMaxPlayer, children);
        node.setScore(bestChild.getScore());
    }

    private Node findBestChild(boolean isMaxPlayer, List<Node> children) {
        if (isMaxPlayer) {
            return Collections.max(children, Comparator.comparing(c -> c.getScore()));
        } else {
            return Collections.min(children, Comparator.comparing(c -> c.getScore()));
        }
    }
}

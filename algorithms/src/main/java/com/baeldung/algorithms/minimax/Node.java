package com.baeldung.algorithms.minimax;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int noOfBones;
    private boolean isMaxPlayer;
    private int score;
    private List<Node> children;

    public Node(int noOfBones, boolean isMaxPlayer) {
        this.noOfBones = noOfBones;
        this.isMaxPlayer = isMaxPlayer;
        children = new ArrayList<>();
    }

    public int getNoOfBones() {
        return noOfBones;
    }

    public void setNoOfBones(int noOfBones) {
        this.noOfBones = noOfBones;
    }

    public boolean isMaxPlayer() {
        return isMaxPlayer;
    }

    public void setMaxPlayer(boolean maxPlayer) {
        isMaxPlayer = maxPlayer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node newNode) {
        children.add(newNode);
    }

}

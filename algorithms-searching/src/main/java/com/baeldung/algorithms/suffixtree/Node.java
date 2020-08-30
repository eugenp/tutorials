package com.baeldung.algorithms.suffixtree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String text;
    private List<Node> children;
    private int position;

    public Node(String word, int position) {
        this.text = word;
        this.position = position;
        this.children = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String printTree(String depthIndicator) {
        String str = "";
        String positionStr = position > -1 ? "[" + String.valueOf(position) + "]" : "";
        str += depthIndicator + text + positionStr + "\n";

        for (int i = 0; i < children.size(); i++) {
            str += children.get(i)
                .printTree(depthIndicator + "\t");
        }
        return str;
    }

    @Override
    public String toString() {
        return printTree("");
    }
}
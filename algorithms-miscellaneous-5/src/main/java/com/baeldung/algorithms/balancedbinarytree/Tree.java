package com.baeldung.algorithms.balancedbinarytree;

public class Tree {
    private final int value;
    private final Tree left;
    private final Tree right;

    public Tree(int value, Tree left, Tree right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public int value() {
        return value;
    }

    public Tree left() {
        return left;
    }

    public Tree right() {
        return right;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s]",
            value,
            left == null ? "null" : left.toString(),
            right == null ? "null" : right.toString()
        );
    }
}

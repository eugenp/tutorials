package com.baeldung.algorithms.quicksort;

public class Partition {
    private int left;
    private int right;

    public Partition(int left, int right) {
        super();
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

}

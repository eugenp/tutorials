package com.baeldung.instancio.generics;

public class Triplet<L, M, R> {
    private L left;
    private M middle;
    private R right;

    public L getLeft() {
        return left;
    }

    public M getMiddle() {
        return middle;
    }

    public R getRight() {
        return right;
    }

    @Override
    public String toString() {
        return String.format("Triplet[left=%s, middle=%s, right=%s]", left, right, middle);
    }
}
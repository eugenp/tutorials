package com.baeldung.instancio.generics;

public class Pair<L, R> {

    private L left;
    private R right;

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public String toString() {
        return String.format("Pair[left=%s, right=%s]", left, right);
    }
}
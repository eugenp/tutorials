package com.baeldung.algorithms.sudoku;

class DancingNode {
    DancingNode L, R, U, D;
    ColumnNode C;

    DancingNode hookDown(DancingNode node) {
        assert (this.C == node.C);
        node.D = this.D;
        node.D.U = node;
        node.U = this;
        this.D = node;
        return node;
    }

    DancingNode hookRight(DancingNode node) {
        node.R = this.R;
        node.R.L = node;
        node.L = this;
        this.R = node;
        return node;
    }

    void unlinkLR() {
        this.L.R = this.R;
        this.R.L = this.L;
    }

    void relinkLR() {
        this.L.R = this.R.L = this;
    }

    void unlinkUD() {
        this.U.D = this.D;
        this.D.U = this.U;
    }

    void relinkUD() {
        this.U.D = this.D.U = this;
    }

    DancingNode() {
        L = R = U = D = this;
    }

    DancingNode(ColumnNode c) {
        this();
        C = c;
    }
}
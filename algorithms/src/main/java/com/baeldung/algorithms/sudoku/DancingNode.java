package com.baeldung.algorithms.sudoku;

class DancingNode {
    DancingNode L, R, U, D;
    ColumnNode C;

    DancingNode hookDown(DancingNode n1) {
        assert (this.C == n1.C);
        n1.D = this.D;
        n1.D.U = n1;
        n1.U = this;
        this.D = n1;
        return n1;
    }

    DancingNode hookRight(DancingNode n1) {
        n1.R = this.R;
        n1.R.L = n1;
        n1.L = this;
        this.R = n1;
        return n1;
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
package com.baeldung.reactor.creation;

public class FibonacciState {
    private int former;
    private int latter;

    public FibonacciState(int former, int latter) {
        this.former = former;
        this.latter = latter;
    }

    public int getFormer() {
        return former;
    }

    public void setFormer(int former) {
        this.former = former;
    }

    public int getLatter() {
        return latter;
    }

    public void setLatter(int latter) {
        this.latter = latter;
    }
}

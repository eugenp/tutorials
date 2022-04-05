package com.baeldung.finalkeyword;

public final class Cat {

    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void methodWithFinalArguments(final int x) {
        // x=1;
    }
}

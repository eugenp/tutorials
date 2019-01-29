package com.baeldung.finalkeyword;

public final class Cat {

    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * final类型的变量是不能进行赋值操作的。
     * @param x
     */
    public void methodWithFinalArguments(final int x) {
        // x=1;
    }
}

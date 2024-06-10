package com.baeldung.deep_vs_shallow_object_copy;

public class Dimension {

    private int value;

    public Dimension(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "value=" + value +
                '}';
    }
}
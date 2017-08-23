package com.baeldung.di;

public class CalculatorSet {

    private Addition addition;

    public Addition getAddition() {
        return addition;
    }

    public void setAddition(Addition addition) {
        System.out.println("Inside addition Setter.");
        this.addition = addition;
    }

    public int calculateAddition(int a, int b) {
        return addition.calculateAddition(a, b);
    }
}
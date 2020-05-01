package com.baeldung.lambdabehave;

public class Calculator {

    private int x;
    private int y;

    Calculator(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int add() {
        return this.x + this.y;
    }

    public int divide(int a, int b) {
        return a / b;
    }

    public int add(int a, int b) {
        return a + b;
    }
}

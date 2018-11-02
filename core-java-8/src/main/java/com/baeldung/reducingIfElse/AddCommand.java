package com.baeldung.reducingIfElse;

public class AddCommand implements Command<Integer, Integer, Integer> {

    private int a;
    private int b;

    @Override
    public Integer execute() {
        return a + b;
    }

    @Override
    public Command<Integer, Integer, Integer> takeInput(Integer a, Integer b) {
        this.a = a;
        this.b = b;
        return this;
    }
}

package com.baeldung.reflection.voidtype;

public class Calculator {
    private int result = 0;

    public int add(int number) {
        return result += number;
    }

    public int sub(int number) {
        return result -= number;
    }

    public void clear() {
        result = 0;
    }

    public void print() {
        System.out.println(result);
    }
}

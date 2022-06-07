package com.baeldung.copy;

public class ShallowCopy implements Cloneable {
    private int a;
    private String b;
    private StringBuilder c;

    public ShallowCopy(int a, String b, StringBuilder c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public ShallowCopy(ShallowCopy copy) {
        this.a = copy.a;
        this.b = copy.b;
        this.c = copy.c;
    }

    public int getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public StringBuilder getC() {
        return c;
    }

    @Override
    public String toString() {
        return "ShallowCopy{" + "a=" + a + ", b='" + b + '\'' + ", c=" + c + '}';
    }
}
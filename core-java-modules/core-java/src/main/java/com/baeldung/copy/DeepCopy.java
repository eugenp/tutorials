package com.baeldung.copy;

public class DeepCopy implements Cloneable {
    private int a;
    private String b;
    private StringBuilder c;

    public DeepCopy(int a, String b, StringBuilder c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public DeepCopy(DeepCopy copy) {
        this.a = copy.a;
        this.b = copy.b;
        this.c = new StringBuilder(copy.c);
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
    public DeepCopy clone() {
        return new DeepCopy(this);
    }

    @Override
    public String toString() {
        return "DeepCopy{" + "a=" + a + ", b='" + b + '\'' + ", c=" + c + '}';
    }
}

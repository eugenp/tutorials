package com.baeldung.math.quadraticequationroot;

public class Polynom {

    private double a;
    private double b;
    private double c;

    public Polynom(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("a can not be equal to 0");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getDiscriminant() {
        return b * b - 4 * a * c;
    }

}

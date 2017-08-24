package com.baeldung.di;

public class Addition {

    public Addition() {
        System.out.println("Inside Addition constructor.");
    }

    public int calculateAddition(int a, int b) {
        int c = a + b;
        System.out.println(" Inside calculateAddition() : " + a + " + " + b
                + " = " + c);
        return c;
    }
}
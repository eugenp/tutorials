package com.baeldung.variableInitialization;

public class A {
    private B b = new B();

    public A() {
        this.b = new B();
    }

    public A(B b) {
        this.b = b;
    }
}
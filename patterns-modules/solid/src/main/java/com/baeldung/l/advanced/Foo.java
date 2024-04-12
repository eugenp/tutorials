package com.baeldung.l.advanced;

public abstract class Foo {

    // precondition: 0 < num <=5
    public void doStuff(int num) {
        if (num <= 0 || num > 5) {
            throw new IllegalArgumentException("Input out of range 1-5");
        }
        // some logic here...
    }

    // precondition: 0 < num <=5
    public void doOtherStuff(int num) {
        if (num <= 0 || num > 5) {
            throw new IllegalArgumentException("Input out of range 1-5");
        }
        // some logic here...
    }

    public abstract Number generateNumber();
}

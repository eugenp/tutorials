package com.baeldung.typechecker;

public class InternedExample {

    boolean areEquals() {
        Box a = new Box();
        Box b = new Box();
        return a == b;
    }

    static class Box {

    }

}

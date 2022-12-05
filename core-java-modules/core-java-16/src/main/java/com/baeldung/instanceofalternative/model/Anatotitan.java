package com.baeldung.instanceofalternative.model;

public class Anatotitan extends Dinosaur {
    // polymorphism
    @Override
    public String move() {
        return "running";
    }

    // non-polymorphism
    public String run() {
        return "running";
    }

}

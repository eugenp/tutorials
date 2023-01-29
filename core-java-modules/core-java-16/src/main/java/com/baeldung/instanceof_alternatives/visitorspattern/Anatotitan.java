package com.baeldung.instanceofalternative.visitorspattern;

public class Anatotitan implements Dino {

    String run() {
        return "running";
    }

    @Override
    public String move(Visitor dinobehave) {
        return dinobehave.visit(this);
    }

}

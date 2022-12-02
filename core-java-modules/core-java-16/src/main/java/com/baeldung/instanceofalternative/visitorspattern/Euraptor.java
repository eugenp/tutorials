package com.baeldung.instanceofalternative.visitorspattern;

public class Euraptor implements Dino {

    String behavior() {
        return "calm";
    }

    @Override
    public String behavior2(Visitor dinobehave) {
        return dinobehave.visit(this);
    }

}

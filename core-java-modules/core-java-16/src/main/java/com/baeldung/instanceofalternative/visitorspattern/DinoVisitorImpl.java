package com.baeldung.instanceofalternative.visitorspattern;

public class DinoVisitorImpl implements Visitor {

    @Override
    public String visit(Anatotitan anatotitan) {
        return anatotitan.behavior();
    }

    @Override
    public String visit(Euraptor euraptor) {
        return euraptor.behavior();
    }

}

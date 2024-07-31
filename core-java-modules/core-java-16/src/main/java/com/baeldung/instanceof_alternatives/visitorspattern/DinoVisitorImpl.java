package com.baeldung.instanceofalternative.visitorspattern;

public class DinoVisitorImpl implements Visitor {

    @Override
    public String visit(Anatotitan anatotitan) {
        return anatotitan.run();
    }

    @Override
    public String visit(Euraptor euraptor) {
        return euraptor.flies();
    }

}

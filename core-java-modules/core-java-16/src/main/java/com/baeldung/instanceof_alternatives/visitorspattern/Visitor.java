package com.baeldung.instanceofalternative.visitorspattern;

public interface Visitor {

    String visit(Anatotitan anatotitan);

    String visit(Euraptor euraptor);

}

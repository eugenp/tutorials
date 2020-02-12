package com.baeldung.visitor;

public interface Visitor {

    void visit(XmlElement xe);

    void visit(JsonElement je);
}

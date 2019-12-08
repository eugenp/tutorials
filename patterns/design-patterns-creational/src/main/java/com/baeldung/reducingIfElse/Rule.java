package com.baeldung.reducingIfElse;

public interface Rule {

    boolean evaluate(Expression expression);

    Result getResult();
}

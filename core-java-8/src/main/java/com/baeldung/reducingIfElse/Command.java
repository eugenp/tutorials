package com.baeldung.reducingIfElse;

public interface Command<A, B, R> {
    R execute();

    Command<A, B, R> takeInput(A a, B b);
}

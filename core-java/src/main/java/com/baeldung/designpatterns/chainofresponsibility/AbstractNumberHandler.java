package com.baeldung.designpatterns.chainofresponsibility;

import java.io.PrintStream;

public abstract class AbstractNumberHandler {

    public PrintStream ps = System.out;

    // next element in chain or responsibility
    public AbstractNumberHandler nextHandler;

    public AbstractNumberHandler(AbstractNumberHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handleNumber(int number) {
        nextHandler.handleNumber(number);
    }
}

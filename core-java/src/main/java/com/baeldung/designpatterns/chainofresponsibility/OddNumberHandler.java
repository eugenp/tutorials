package com.baeldung.designpatterns.chainofresponsibility;


public class OddNumberHandler extends AbstractNumberHandler {
    public OddNumberHandler(AbstractNumberHandler nextLogger) {
        super(nextLogger);
    }

    @Override
    public void handleNumber(int number) {
        if(number % 2 == 1) {
            ps.println(number + " is odd");
        } else {
            nextHandler.handleNumber(number);
        }
    }
}

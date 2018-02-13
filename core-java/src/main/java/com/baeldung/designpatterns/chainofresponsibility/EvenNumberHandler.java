package com.baeldung.designpatterns.chainofresponsibility;


public class EvenNumberHandler extends AbstractNumberHandler {
    public EvenNumberHandler(AbstractNumberHandler nextLogger) {
        super(nextLogger);
    }

    @Override
    public void handleNumber(int number) {
        if (number % 2 == 0) {
            ps.println(number + " is even");
        } else {
            nextHandler.handleNumber(number);
        }
    }
}

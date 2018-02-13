package com.baeldung.designpatterns.chainofresponsibility;


public class PrimeNumberHandler extends AbstractNumberHandler {
    public PrimeNumberHandler(AbstractNumberHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleNumber(int number) {

        if (isPrime(number)) {
            ps.println(number + " is prime");
        } else {
            nextHandler.handleNumber(number);
        }
    }

    private boolean isPrime(int number) {
        // check whether number is prime or not
        int pivot = number / 2;
        if (pivot == 0 || pivot == 1) {
            return false;
        }

        for (int i = 2; i <= pivot; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}

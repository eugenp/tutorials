package com.baeldung.architecture.hexagonal;


import com.baeldung.architecture.hexagonal.business.InterestCalculatorUseCase;
import com.baeldung.architecture.hexagonal.factory.InterestRateFactory;

public class InterestCalculatorMain {

    private static InterestCalculatorUseCase app =
            new InterestCalculatorUseCase(InterestRateFactory.getMockRateRepository());

    public static void main(String[] args) {
        getInterestAmount(1000);


    }

    public static double getInterestAmount(double amount) {
        return app.interest(amount);
    }
}

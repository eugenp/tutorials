package com.baeldung.architecture.hexagonal.printer.impl;


import com.baeldung.architecture.hexagonal.business.InterestCalculatorUseCase;
import com.baeldung.architecture.hexagonal.factory.InterestRateFactory;
import com.baeldung.architecture.hexagonal.printer.ConsolePrinter;

public class ConsolePrinterImpl implements ConsolePrinter {

    private static InterestCalculatorUseCase app =
            new InterestCalculatorUseCase(InterestRateFactory.getMockRateRepository());

    @Override
    public void printInterestAmount(double amount) {
        System.out.println(app.interest(amount));
    }
}

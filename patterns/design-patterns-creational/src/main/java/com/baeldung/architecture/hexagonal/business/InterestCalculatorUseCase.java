package com.baeldung.architecture.hexagonal.business;

import com.baeldung.architecture.hexagonal.repository.InterestRateRepository;

public class InterestCalculatorUseCase {

    private InterestRateRepository rateRepository;

    public InterestCalculatorUseCase(InterestRateRepository r) {
        super();
        rateRepository = r;
    }

    public double interest(double amount) {
        double rate = rateRepository.getInterestRate(amount);
        return amount * rate;
    }
}

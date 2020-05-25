package com.baeldung.architecture.hexagonal.repository.impl;

import com.baeldung.architecture.hexagonal.repository.InterestRateRepository;

public class MockInterestRateRepository implements InterestRateRepository {

    @Override
    public double getInterestRate(double amount) {
        if (amount <= 1000) return 0.03;
        if (amount <= 10000) return 0.04;
        return 0.07;
    }
}

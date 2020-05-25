package com.baeldung.architecture.hexagonal.factory;

import com.baeldung.architecture.hexagonal.repository.InterestRateRepository;
import com.baeldung.architecture.hexagonal.repository.impl.MockInterestRateRepository;

public class InterestRateFactory {
    public InterestRateFactory() {
        super();
    }

    public static InterestRateRepository getMockRateRepository() {
        return new MockInterestRateRepository();
    }
}

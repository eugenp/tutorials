package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.business.InterestCalculatorUseCase;
import com.baeldung.architecture.hexagonal.repository.impl.MockInterestRateRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterestCalculatorUsecaseTest {

    @Test
    void whenAmountLessThanOrEquals1000_ThenInterest3Percent() {
        InterestCalculatorUseCase useCase = new InterestCalculatorUseCase(new MockInterestRateRepository());
        assertEquals(30, useCase.interest(1000));
    }

    @Test
    void whenAmountLessThanOrEquals10000_ThenInterest4Percent() {
        InterestCalculatorUseCase useCase = new InterestCalculatorUseCase(new MockInterestRateRepository());
        assertEquals(400, useCase.interest(10000));
    }

    @Test
    void whenAmountGreaterThan10000_ThenInterest7Percent() {
        InterestCalculatorUseCase useCase = new InterestCalculatorUseCase(new MockInterestRateRepository());
        assertEquals(1050, useCase.interest(15000));
    }
}

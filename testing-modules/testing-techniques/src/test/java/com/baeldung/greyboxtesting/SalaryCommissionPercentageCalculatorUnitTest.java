package com.baeldung.greyboxtesting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.baeldung.greyboxtesting.SalaryCommissionPercentageCalculator.*;
import static com.baeldung.greyboxtesting.SalaryCommissionPercentageCalculator.Level.*;
import static com.baeldung.greyboxtesting.SalaryCommissionPercentageCalculator.SalesImpact.*;
import static com.baeldung.greyboxtesting.SalaryCommissionPercentageCalculator.Seniority.*;
import static com.baeldung.greyboxtesting.SalaryCommissionPercentageCalculator.Type.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SalaryCommissionPercentageCalculatorUnitTest {

    private SalaryCommissionPercentageCalculator testTarget = new SalaryCommissionPercentageCalculator();

    @ParameterizedTest
    @MethodSource("provideReferenceTestScenarioTable")
    void givenReferenceTable_whenCalculateAverageCommission_thenReturnExpectedResult(Level level, Type type, Seniority seniority, SalesImpact impact, double expected) {
        BigDecimal got = testTarget.calculate(level, type, seniority, impact);
        assertEquals(BigDecimal.valueOf(expected), got);
    }

    private static Stream<Arguments> provideReferenceTestScenarioTable() {
        return Stream.of(
                Arguments.of(L1, FULL_TIME_COMMISSIONED, JR, LOW, 0.26),
                Arguments.of(L1, CONTRACTOR, SR, MEDIUM, 0.12),
                Arguments.of(L1, FREELANCER, MID, HIGH, 0.11),
                Arguments.of(L2, FULL_TIME_COMMISSIONED, SR, HIGH, 0.18),
                Arguments.of(L2, CONTRACTOR, MID, LOW, 0.11),
                Arguments.of(L2, FREELANCER, JR, MEDIUM, 0.24),
                Arguments.of(L3, FULL_TIME_COMMISSIONED, MID, MEDIUM, 0.17),
                Arguments.of(L3, CONTRACTOR, JR, HIGH, 0.28),
                Arguments.of(L3, FREELANCER, SR, LOW, 0.12)
        );
    }
}
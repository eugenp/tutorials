package com.baeldung.hexagonal.banking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AccountUnitTest {

    @ParameterizedTest
    @MethodSource("dataToValidatePins")
    public void givenAccountsAndPinsAttempts_shouldReturnValidationResultsAccordingly(Account givenAccount, int givenPin, boolean expectedResult) {

        assertThat(givenAccount.validatePin(givenPin)).isEqualTo(expectedResult);

    }

    private static Stream<Arguments> dataToValidatePins() {
        return Stream.of(Arguments.of(StubAccountFactory.stubDefaultAccount(), 123, true), Arguments.of(StubAccountFactory.stubDefaultAccount(), 999, false));
    }

    @ParameterizedTest
    @MethodSource("dataToValidateCredits")
    public void shouldIncreaseBalance_withGivenDepositAmount(Account givenAccount, BigDecimal givenAmount, BigDecimal expectedNewBalance) {

        givenAccount.creditAccount(givenAmount);
        assertThat(givenAccount.getBalance()
            .compareTo(expectedNewBalance)).isEqualTo(0);

    }

    private static Stream<Arguments> dataToValidateCredits() {
        return Stream.of(Arguments.of(StubAccountFactory.stubDefaultAccount(), BigDecimal.TEN, BigDecimal.valueOf(11D)), Arguments.of(StubAccountFactory.stubDefaultAccount(), BigDecimal.ZERO, BigDecimal.ONE));
    }

    @ParameterizedTest
    @MethodSource("dataToValidateDebits")
    public void shouldThrowException_whenInvalidDebitAmounts(Account givenAccount, BigDecimal givenAmount, boolean expectedResult, BigDecimal expectedNewBalance) {

        boolean actualResult = givenAccount.debitAccount(givenAmount);
        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(givenAccount.getBalance()
            .compareTo(expectedNewBalance)).isEqualTo(0);

    }

    private static Stream<Arguments> dataToValidateDebits() {
        return Stream.of(Arguments.of(StubAccountFactory.stubDefaultAccount(), BigDecimal.ONE, true, BigDecimal.ZERO), Arguments.of(StubAccountFactory.stubDefaultAccount(), BigDecimal.TEN, false, BigDecimal.ONE),
            Arguments.of(StubAccountFactory.stubDefaultAccount(), BigDecimal.ZERO, false, BigDecimal.ONE), Arguments.of(StubAccountFactory.stubDefaultAccount(), BigDecimal.TEN.negate(), false, BigDecimal.ONE));
    }

}

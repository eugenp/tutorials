package com.baeldung.hexagonal.banking.application.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.domain.StubAccountFactory;
import com.baeldung.hexagonal.banking.input.port.TransferMoneyCommand;
import com.baeldung.hexagonal.banking.output.port.AccountStatePort;

public class TransferMoneyServiceUnitTest {

    private TransferMoneyService target;
    private AccountStatePort mockAccountStatePort;
    
    private final Long sourceAccountNumber = 10L;
    private final Long targetAccountNumber = 20L;

    @BeforeEach
    void setUp() {
        mockAccountStatePort = mock(AccountStatePort.class);
        target = new TransferMoneyService(mockAccountStatePort);
    }

    @Test
    void shouldFail_IfAnyAccountIsNotValid() {

        TransferMoneyCommand invalidCommand = givenTransferCommandWithInvalidAccount();
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            target.transferMoney(invalidCommand);
        });
    }

    @Test
    void shouldFail_whenGivenPinDoesNotMatchSourceAccountPin() {

        
        givenAccountWithNumberAndBalance(sourceAccountNumber, BigDecimal.TEN);
        TransferMoneyCommand invalidCommand = givenCommandWith(sourceAccountNumber, targetAccountNumber, BigDecimal.ONE, 9999);
        Assertions.assertThrows(InvalidPinException.class, () -> {
            target.transferMoney(invalidCommand);
        });
    }

    @Test
    void shouldFail_whenGivenCommandWithSourceAccountHasInsuficientFunds() {


        givenAccountWithNumberAndBalance(sourceAccountNumber, BigDecimal.ONE);
        givenAccountWithNumberAndBalance(targetAccountNumber, BigDecimal.ONE);

        TransferMoneyCommand invalidCommand = givenCommandWith(sourceAccountNumber, targetAccountNumber, BigDecimal.TEN, 1234);
        Assertions.assertThrows(NotEnoughBalanceException.class, () -> {
            target.transferMoney(invalidCommand);
        });
    }

    @Test
    void shouldMoveTheCash_whenGivenCommandWithValidAccountAndSuficientFunds() {


        Account sourceAccount = givenAccountWithNumberAndBalance(sourceAccountNumber, BigDecimal.TEN);
        Account targetAccount = givenAccountWithNumberAndBalance(targetAccountNumber, BigDecimal.ONE);

        TransferMoneyCommand validCommand = givenCommandWith(sourceAccountNumber, targetAccountNumber, BigDecimal.TEN, 1234);

        target.transferMoney(validCommand);

        verify(mockAccountStatePort).createOrUpdateAccount(sourceAccount);
        verify(mockAccountStatePort).createOrUpdateAccount(targetAccount);
    }

    private TransferMoneyCommand givenTransferCommandWithInvalidAccount() {
        TransferMoneyCommand stubCommand = new TransferMoneyCommand(0L, 1L, BigDecimal.TEN, 1234);
        when(mockAccountStatePort.loadAccount(0L)).thenThrow(new EntityNotFoundException());
        return stubCommand;
    }

    private TransferMoneyCommand givenCommandWith(Long sourceAccount, Long targetAccount, BigDecimal transferAmount, int pin) {

        return new TransferMoneyCommand(sourceAccount, targetAccount, transferAmount, pin);

    }

    private Account givenAccountWithNumberAndBalance(Long givenAccountNumber, BigDecimal givenBalance) {
        Optional<Account> stubAccount = Optional.of(new StubAccountFactory().withAccountNumber(givenAccountNumber)
            .withBalance(givenBalance)
            .build());
        when(mockAccountStatePort.loadAccount(givenAccountNumber)).thenReturn(stubAccount);
        return stubAccount.get();
    }

}

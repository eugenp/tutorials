package com.baeldung.hexagonal.architecture.example.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.hexagonal.architecture.example.domain.AccountAssembler;
import com.baeldung.hexagonal.architecture.example.domain.TransactionRequest;
import com.baeldung.hexagonal.architecture.example.model.Account;
import com.baeldung.hexagonal.architecture.example.repository.AccountRepository;
import com.baeldung.hexagonal.architecture.example.service.AccountService;
import com.baeldung.hexagonal.architecture.example.service.exception.NoDataFoundException;
import com.baeldung.hexagonal.architecture.example.service.exception.TransactionNotAllowed;
import com.baeldung.hexagonal.architecture.example.service.validation.TransactionRequestValidator;

class TransactionValidatorUnitTest {

    private final AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private final AccountAssembler accountAssembler = Mockito.mock(AccountAssembler.class);
    private final AccountService accountService = new AccountService(accountAssembler, accountRepository);
    private final TransactionRequestValidator transactionRequestValidator = new TransactionRequestValidator(accountService);

    @Test
    public void whenNonExistingTargetAccountReferenced_thenThrowTargetAccountNotFoundException() {
        Mockito.when(accountRepository.findById(1L))
            .thenReturn(Optional.ofNullable(getAccount1()));
        Mockito.when(accountRepository.findById(2L))
            .thenReturn(Optional.ofNullable(null));

        Exception exception = Assertions.assertThrows(NoDataFoundException.class, () -> {
            transactionRequestValidator.validateTransactionRequest(getTransactionRequest1());
        });
        String expectedMessage = "Account not found with AccountNo: 2";
        assertTrue(exception.getMessage()
            .contains(expectedMessage));
    }

    @Test
    public void whenNonExistingSourceAccountReferenced_thenThrowSourceAccountNotFoundException() {
        Mockito.when(accountRepository.findById(1L))
            .thenReturn(Optional.ofNullable(null));
        Mockito.when(accountRepository.findById(2L))
            .thenReturn(Optional.ofNullable(getAccount2()));
        Exception exception = assertThrows(NoDataFoundException.class, () -> {
            transactionRequestValidator.validateTransactionRequest(getTransactionRequest1());
        });
        String expectedMessage = "Account not found with AccountNo: 1";
        assertTrue(exception.getMessage()
            .contains(expectedMessage));
    }

    @Test
    public void whenNotEnoughBalance_thenThrowNotEnoughBalanceException() {
        TransactionRequest transactionRequest = getTransactionRequest1();
        transactionRequest.setAmount(new BigDecimal(1001));
        Mockito.when(accountRepository.findById(1L))
            .thenReturn(Optional.ofNullable(getAccount1()));
        Mockito.when(accountRepository.findById(2L))
            .thenReturn(Optional.ofNullable(getAccount2()));
        Exception exception = assertThrows(TransactionNotAllowed.class, () -> {
            transactionRequestValidator.validateTransactionRequest(transactionRequest);
        });
        String expectedMessage = "Source Account Does not have enough balance";
        assertTrue(exception.getMessage()
            .contains(expectedMessage));
    }

    Account getAccount1() {
        Account account1 = new Account();
        account1.setId(1L);
        account1.setBalance(new BigDecimal("1000"));
        return account1;
    }

    Account getAccount2() {
        Account account2 = new Account();
        account2.setId(2L);
        account2.setBalance(new BigDecimal("1000"));
        return account2;
    }

    TransactionRequest getTransactionRequest1() {
        TransactionRequest transactionRequest1 = new TransactionRequest();
        transactionRequest1.setAmount(new BigDecimal(150));
        transactionRequest1.setSourceAccountNo(1L);
        transactionRequest1.setTargetAccountNo(2L);
        return transactionRequest1;
    }

}
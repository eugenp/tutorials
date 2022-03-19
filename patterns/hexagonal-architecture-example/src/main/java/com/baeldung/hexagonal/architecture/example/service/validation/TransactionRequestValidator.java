package com.baeldung.hexagonal.architecture.example.service.validation;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.example.domain.TransactionRequest;
import com.baeldung.hexagonal.architecture.example.model.Account;
import com.baeldung.hexagonal.architecture.example.service.AccountService;
import com.baeldung.hexagonal.architecture.example.service.exception.TransactionNotAllowed;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionRequestValidator {

    private final AccountService accountService;

    public void validateTransactionRequest(TransactionRequest request) {
        Account sourceAccount = accountService.getAccount(request.getSourceAccountNo());
        Account targetAccount = accountService.getAccount(request.getTargetAccountNo());
        checkSourceAccountHasEnoughBalance(sourceAccount, request.getAmount());
    }

    public void checkSourceAccountHasEnoughBalance(Account sourceAccount, BigDecimal amount) {
        if (sourceAccount.getBalance()
            .compareTo(amount) == -1) {
            throw new TransactionNotAllowed("Source Account Does not have enough balance");
        }
    }
}

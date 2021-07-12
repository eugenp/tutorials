package com.baeldung.application.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.domain.port.incoming.Deposit;
import com.baeldung.domain.port.incoming.Withdraw;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final Deposit depositUseCase;
    private final Withdraw withdrawUseCase;

    public AccountController(Deposit depositUseCase, Withdraw withdrawUseCase) {
        this.depositUseCase = depositUseCase;
        this.withdrawUseCase = withdrawUseCase;
    }

    @PostMapping(value = "/{accountNo}/deposit/{depositAmount}")
    void deposit(@PathVariable final Long accountNo, @PathVariable final BigDecimal depositAmount) {
        depositUseCase.deposit(accountNo, depositAmount);
    }

    @PostMapping(value = "/{accountNo}/withdraw/{withdrawalAmount}")
    void withdraw(@PathVariable final Long accountNo, @PathVariable final BigDecimal withdrawalAmount) {
        withdrawUseCase.withdraw(accountNo, withdrawalAmount);
    }
}

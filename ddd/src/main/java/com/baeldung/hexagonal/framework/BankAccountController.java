package com.baeldung.hexagonal.framework;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.application.Depositable;
import com.baeldung.hexagonal.application.Withdrawable;

@RestController
@RequestMapping("/account")
public class BankAccountController {

    private final Depositable depositable;
    private final Withdrawable withdrawable;

    public BankAccountController(Depositable depositable, Withdrawable withdrawable) {
        this.depositable = depositable;
        this.withdrawable = withdrawable;
    }

    @PostMapping(value = "/{id}/deposit/{amount}")
    void deposit(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
        depositable.deposit(id, amount);
    }

    @PostMapping(value = "/{id}/withdraw/{amount}")
    void withdraw(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
        withdrawable.withdraw(id, amount);
    }
}
package com.baeldung.hexagonal.saving.adapter.in.web;

import com.baeldung.hexagonal.saving.application.port.in.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/savingAccount")
public class SavingAccountApiResource {

    @Autowired
    private SavingAccountService savingAccountService;

    @GetMapping("/balance/{accountNumber}")
    public BigDecimal getBalance(@PathVariable(value = "accountNumber") String accountNumber) {
        return savingAccountService.getBalance(accountNumber);
    }
}

package com.baeldung.hexagonal.framework;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.application.service.BankAccountService;

@RestController
@RequestMapping("/account")
public class BankAccountController {
        
    private BankAccountService service;
    
    public BankAccountController(BankAccountService service) {
        this.service = service;
    }

    @PostMapping(value = "/{id}/deposit/{amount}")
    void deposit(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
        service.deposit(id, amount);
    }

    @PostMapping(value = "/{id}/withdraw/{amount}")
    void withdraw(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
        service.withdraw(id, amount);
    }
}
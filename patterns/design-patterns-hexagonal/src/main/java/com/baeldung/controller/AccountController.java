package com.baeldung.controller;

import com.baeldung.model.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baeldung.interfaces.IAccountService;

import java.util.List;

@RestController
public class AccountController{
    @Autowired
    private IAccountService accountService;

    @PostMapping("/account/create")
    public void createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/account/{accountNumber}")
    public Account getAccount(@PathVariable Long accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @GetMapping
    public List<Account> allAccounts() {
        return accountService.allAccounts();
    }
}

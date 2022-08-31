package com.baeldung.security.opa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.security.opa.domain.Account;
import com.baeldung.security.opa.service.AccountService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountService accountService;
    
    @GetMapping("/account/{accountId}")
    public Mono<Account> getAccount(@PathVariable("accountId") String accountId) {
        return accountService.findByAccountId(accountId);
    }
}

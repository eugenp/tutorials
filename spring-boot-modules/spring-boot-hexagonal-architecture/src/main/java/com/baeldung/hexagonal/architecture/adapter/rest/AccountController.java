package com.baeldung.hexagonal.architecture.adapter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.domain.model.Account;
import com.baeldung.hexagonal.architecture.port.AccountService;

/**
 * The class defines a REST controller which is a primary adapter and invokes the inbound port of the application.
 */
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private AccountService AccountService;

    @Autowired
    public AccountController(AccountService AccountService) {
        this.AccountService = AccountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        return new ResponseEntity<>(AccountService.getAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{AccountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer AccountId) {
        return new ResponseEntity<>(AccountService.getAccountById(AccountId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody Account Account) {
        return new ResponseEntity<>(AccountService.addAccount(Account), HttpStatus.CREATED);
    }

    @DeleteMapping("/{AccountId}")
    public ResponseEntity<Account> removeAccount(@PathVariable Integer AccountId) {
        return new ResponseEntity<>(AccountService.removeAccount(AccountId), HttpStatus.OK);
    }
}

package com.baeldung.inprogress.hexagonal.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @GetMapping(value = "/{id}", produces = "application/json")
    public Account getAccount(@PathVariable String id) {
        return getAccountById(id);
    }

    private Account getAccountById(String id) {
        return new Account(id, "dummy-account");
    }

}

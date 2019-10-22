package com.baeldung.hexagonal.adaptors;

import com.baeldung.hexagonal.model.Account;
import com.baeldung.hexagonal.model.AccountNotFoundException;
import com.baeldung.hexagonal.ports.entry.AccountPort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {
    private AccountPort accountService;

    AccountController(AccountPort accountService) {
        this.accountService = accountService;
        this.accountService.openAccount(BigDecimal.ZERO);
    }

    @GetMapping
    @RequestMapping("/account/{id}")
    public Account getAccount(@PathVariable("id") Long id) throws AccountNotFoundException {
        return accountService.getAccount(id);
    }

    @PostMapping
    @RequestMapping("/account/{id}/deposit/{amount}")
    public BigDecimal deposit(@PathVariable("id") Long id, @PathVariable("amount") BigDecimal amount) throws AccountNotFoundException {
        return accountService.depositMoney(id, amount);
    }

    @GetMapping
    @RequestMapping("/account/search")
    public List<Account> accountSearch(@RequestParam(value = "moreThanBalance") BigDecimal moreThanBalance) {
        return accountService.searchByBalanceMoreThan(moreThanBalance);
    }

}
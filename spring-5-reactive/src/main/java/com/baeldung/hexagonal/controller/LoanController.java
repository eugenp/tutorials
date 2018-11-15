package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.model.Loan;
import com.baeldung.hexagonal.ports.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/loans")
    public Mono<Loan> getLoan(
            @RequestParam(value = "amount") double amount,
            @RequestParam(value = "rate") double rate,
            @RequestParam(value = "numberOfMonths") int numberOfMonths) {

        Loan loan = loanService.getLoan(amount, rate, numberOfMonths);
        return Mono.just(loan);
    }
}
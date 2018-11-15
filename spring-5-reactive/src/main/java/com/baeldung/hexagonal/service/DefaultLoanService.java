package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.model.Loan;
import com.baeldung.hexagonal.ports.LoanPersistService;
import com.baeldung.hexagonal.ports.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultLoanService implements LoanService {
    @Autowired
    private LoanPersistService loanPersistService;

    @Override
    public Loan getLoan(double amount, double rate, int numberOfMonths) {

        double interest = amount * rate * numberOfMonths / 100;

        Loan loan = new Loan();
        loan.setAmount(amount);
        loan.setRate(rate);
        loan.setNumberOfMonths(numberOfMonths);
        loan.setInterest(interest);

        return loanPersistService.save(loan);
    }
}
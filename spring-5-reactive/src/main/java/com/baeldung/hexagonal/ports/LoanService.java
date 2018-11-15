package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.model.Loan;



public interface LoanService {
    Loan getLoan(double amount, double rate, int numberOfMonths);
}
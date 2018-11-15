package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.model.Loan;

public interface LoanPersistService {
    Loan save(Loan loan);
}
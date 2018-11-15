package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.model.Loan;
import com.baeldung.hexagonal.ports.LoanPersistService;
import org.springframework.stereotype.Service;


@Service
public class MockLoanPersistService implements LoanPersistService {
    @Override
    public Loan save(Loan loan) {
        return loan;
    }
}
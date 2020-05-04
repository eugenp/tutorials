package com.baeldung.hexagonal.upstream;

import com.baeldung.hexagonal.banking.domain.Company;
import com.baeldung.hexagonal.banking.domain.Person;

;

public interface BankPort {

   


    public void credit(Long accountNumber, double amount);

    public boolean debit(Long accountNumber, double amount);
}

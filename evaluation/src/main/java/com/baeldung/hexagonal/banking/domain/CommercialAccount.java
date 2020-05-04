package com.baeldung.hexagonal.banking.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CommercialAccount extends Account {

    private List<Person> authorizedUsers;

    public CommercialAccount(Company company, int pin, BigDecimal startingDeposit) {
        super(company, pin, startingDeposit);
    }
    
    public CommercialAccount(Long accountNumber, Company company, int pin, BigDecimal startingDeposit) {
        super(accountNumber, company, pin, startingDeposit);
    }

    protected void addAuthorizedUser(Person person) {
        if (authorizedUsers == null)
            authorizedUsers = new ArrayList<>();
        if (person != null)
            authorizedUsers.add(person);
    }

    public boolean isAuthorizedUser(Person person) {
        return authorizedUsers.contains(person);
    }
}

package com.baeldung.hexagonal.architecture.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.port.SignUpServiceInputPort;
import com.baeldung.hexagonal.architecture.domain.dto.Account;
import com.baeldung.hexagonal.architecture.port.Persistor;

@Service
public class SignupService implements SignUpServiceInputPort{
    
    @Autowired
    Persistor persistor;

    public boolean signup(Account account) {
        Account newAccount = new Account();
        newAccount.setFirstName(account.getFirstName().toUpperCase());
        newAccount.setLastName(account.getLastName().toUpperCase());
        return persistor.persist(newAccount);
    }
}

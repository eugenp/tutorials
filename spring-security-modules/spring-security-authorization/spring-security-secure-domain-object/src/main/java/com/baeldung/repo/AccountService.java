package com.baeldung.repo;

import java.util.Optional;

import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Service;

import com.baeldung.model.Account;

@Service
public class AccountService {

    @AuthorizeReturnObject
    public Optional<Account> getAccountByIban(String iban) {
        return Optional.of(new Account("XX1234567809", 2345.6));
    }
}

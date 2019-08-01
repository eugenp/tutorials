package com.baeldung.hexagonal.central.repository;

import com.baeldung.hexagonal.central.domain.Account;

public interface IAccountRepository {

    public void save(Account account);

}

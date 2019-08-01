package com.baeldung.hexagonal.output;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.baeldung.hexagonal.central.domain.Account;
import com.baeldung.hexagonal.central.repository.IAccountRepository;

public class InMemoryAccountRepository implements IAccountRepository {

    private List<Account> accountList = new ArrayList<>();

    @Override
    public void save(Account account) {

        account.setId(UUID.randomUUID());

        accountList.add(account);

    }

}

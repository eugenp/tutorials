package com.baeldung.hexagonal_architecture.data;

import java.util.Arrays;
import java.util.List;

import com.baeldung.hexagonal_architecture.entity.Account;

public class AccountsData {

    private AccountsData() {
        // Private constructor so that no one can initialize this class
    }

    public static final List<Account> accountsData = Arrays.asList( // @formatter:off
      new Account(1, "test1", 10000), 
      new Account(2, "test2", 100000), 
      new Account(3, "test3", 50000) //@formatter:off
      );
}

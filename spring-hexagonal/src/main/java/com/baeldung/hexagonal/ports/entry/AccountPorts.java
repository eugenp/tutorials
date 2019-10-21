package com.baeldung.hexagonal.ports.entry;

import com.baeldung.hexagonal.model.Account;
import com.baeldung.hexagonal.model.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountPorts {
    Account openAccount(BigDecimal initialDeposit);

    Account getAccount(Long id) throws AccountNotFoundException;

    BigDecimal depositMoney(Long id, BigDecimal amount) throws AccountNotFoundException;

    BigDecimal checkBalance(Long id) throws AccountNotFoundException;

    List<Account> searchByBalanceMoreThan(BigDecimal moreThanBalance);
}

package com.baeldung.hexagonal.core;

import java.math.BigDecimal;
import java.util.List;

/**
 * Core domain interfaces to the ports
 * @author ysharma2512
 *
 */
public interface BankAccountService {
    public BigDecimal getBalance();

    public List<Transaction> getStatement();

    public boolean deposit(BigDecimal amount);

    public boolean withDraw(BigDecimal amount);

}

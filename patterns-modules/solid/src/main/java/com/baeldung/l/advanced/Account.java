package com.baeldung.l.advanced;

import java.math.BigDecimal;

public abstract class Account {
    protected abstract void deposit(BigDecimal amount);

    /**
     * Reduces the account balance by the specified amount
     * provided given amount > 0 and account meets minimum available
     * balance criteria.
     *
     * @param amount
     */
    protected abstract void withdraw(BigDecimal amount);

}

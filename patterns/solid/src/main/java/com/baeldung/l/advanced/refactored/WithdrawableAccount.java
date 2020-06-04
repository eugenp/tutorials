package com.baeldung.l.advanced.refactored;

import java.math.BigDecimal;

public abstract class WithdrawableAccount extends Account {
    protected abstract void withdraw(BigDecimal amount);
}

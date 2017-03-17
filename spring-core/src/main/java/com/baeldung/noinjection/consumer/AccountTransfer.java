package com.baeldung.noinjection.consumer;

import java.math.BigDecimal;

public interface AccountTransfer {
    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);
}

package com.baeldung.noinjection.consumer;

import java.math.BigDecimal;

public interface IAccountTransfer {
    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);
}

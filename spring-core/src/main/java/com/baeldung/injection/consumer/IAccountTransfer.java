package com.baeldung.injection.consumer;

import java.math.BigDecimal;

public interface IAccountTransfer {
    boolean deposit(BigDecimal amount);

    boolean withdraw(BigDecimal amount);
}

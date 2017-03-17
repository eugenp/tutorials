package com.baeldung.injection.consumer;

import java.math.BigDecimal;

public interface AccountTransfer {
    boolean deposit(BigDecimal amount);

    boolean withdraw(BigDecimal amount);
}

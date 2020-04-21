package com.baeldung.hexExample.application.port.incoming;

import java.math.BigDecimal;

public interface DepositUseCase {
    void deposit(Long id, BigDecimal amount);
}
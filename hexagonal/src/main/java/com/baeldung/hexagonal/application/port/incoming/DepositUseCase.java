package com.baeldung.hexagonal.application.port.incoming;

import java.math.BigDecimal;

public interface DepositUseCase {
    void deposit(Long id, BigDecimal amount);
}
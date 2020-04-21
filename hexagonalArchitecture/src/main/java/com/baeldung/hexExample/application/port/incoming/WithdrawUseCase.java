package com.baeldung.hexExample.application.port.incoming;

import java.math.BigDecimal;

public interface WithdrawUseCase {
    boolean withdraw(Long id, BigDecimal amount);
}
package com.baeldung.hexarch.application.port.incoming;

import java.math.BigDecimal;

public interface PaymentCase {
    void pay(Long id, BigDecimal amount);
}
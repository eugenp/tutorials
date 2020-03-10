package com.baeldung.hexagonal.application;

import java.math.BigDecimal;

public interface Depositable {
    void deposit(Long id, BigDecimal amount);
}
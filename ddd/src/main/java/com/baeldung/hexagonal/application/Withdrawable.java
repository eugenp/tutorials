package com.baeldung.hexagonal.application;

import java.math.BigDecimal;

public interface Withdrawable {
    boolean withdraw(Long id, BigDecimal amount);
}
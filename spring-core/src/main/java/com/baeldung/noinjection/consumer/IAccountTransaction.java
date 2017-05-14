package com.baeldung.noinjection.consumer;

import java.math.BigDecimal;

public interface IAccountTransaction {
    void validateTransaction(BigDecimal amount);

}

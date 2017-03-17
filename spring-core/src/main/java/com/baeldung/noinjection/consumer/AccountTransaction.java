package com.baeldung.noinjection.consumer;

import java.math.BigDecimal;

public interface AccountTransaction {
    void validateTransaction(BigDecimal amount);

}

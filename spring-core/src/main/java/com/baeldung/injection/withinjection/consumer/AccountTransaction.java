package com.baeldung.injection.withinjection.consumer;

import java.math.BigDecimal;

public interface AccountTransaction {
    void validateTransaction(BigDecimal amount);

}

package com.baeldung.injection.withoutinjection.consumer;

import java.math.BigDecimal;

public interface AccountTransaction {
    void validateTransaction(BigDecimal amount);

}

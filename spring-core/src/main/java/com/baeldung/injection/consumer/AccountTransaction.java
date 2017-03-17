package com.baeldung.injection.consumer;

import java.math.BigDecimal;

public interface AccountTransaction {

    void validateTransaction(BigDecimal amount);

}

package com.baeldung.injection.consumer;

import java.math.BigDecimal;

public interface IAccountTransaction {

    void validateTransaction(BigDecimal amount);

}

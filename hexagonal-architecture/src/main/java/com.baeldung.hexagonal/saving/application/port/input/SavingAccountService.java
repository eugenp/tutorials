package com.baeldung.hexagonal.saving.application.port.in;

import java.math.BigDecimal;

public interface SavingAccountService {

    BigDecimal getBalance(String accountNumber);
}

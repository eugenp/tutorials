package com.baeldung.hexagonal.banking.input.port;

import java.math.BigDecimal;

public interface GetAccountBalanceQueryPort {

    public BigDecimal getBalance(Long accountNumber);

}

package com.baeldung.domain.port.incoming;

import java.math.BigDecimal;

public interface Withdraw {

    boolean withdraw(Long accountNo, BigDecimal withdrawalAmount);
}

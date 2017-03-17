package com.baeldung.injection.withinjection.consumer;

import java.math.BigDecimal;

public interface AccountTransfer {
	boolean deposit(BigDecimal amount);

	boolean withdraw(BigDecimal amount);
}

package com.baeldung.hexarch.application.port.incoming;

import java.math.BigDecimal;

public interface SwipeCase {
    boolean swipe(Long id, BigDecimal amount, boolean isCash);
}
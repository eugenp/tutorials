package com.baeldung.domain.ports.incoming;

import java.math.BigDecimal;

public interface BuyCar {
    boolean buyCar(long carId, BigDecimal propsedAmount);
}

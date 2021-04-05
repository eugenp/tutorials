package com.baeldung.pattern.hexagonal.product.application.port.inbound;

import java.math.BigDecimal;

public interface GetPriceUseCase {
    BigDecimal getPrice(String productId);
}

package com.baeldung.concurrent.virtualthreads.vs.webflux.virtualthreads;

import java.math.BigDecimal;

class DiscountService {

    public BigDecimal discountForProduct(String productId) {
        return BigDecimal.valueOf(10);
    }

}

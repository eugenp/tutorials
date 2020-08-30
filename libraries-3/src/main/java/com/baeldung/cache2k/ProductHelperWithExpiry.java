package com.baeldung.cache2k;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class ProductHelperWithExpiry {

    private Cache<String, Integer> cachedDiscounts;

    private int cacheMissCount = 0;

    public ProductHelperWithExpiry() {
        cachedDiscounts = Cache2kBuilder.of(String.class, Integer.class)
            .name("discount-expiry")
            .expireAfterWrite(5, TimeUnit.MILLISECONDS)
            .entryCapacity(100)
            .build();

    }

    public Integer getDiscount(String productType) {
        Integer discount = cachedDiscounts.get(productType);
        if (Objects.isNull(discount)) {
            cacheMissCount++;
            discount = "Sports".equalsIgnoreCase(productType) ? 20 : 10;
            cachedDiscounts.put(productType, discount);
        }
        return discount;
    }

    public int getCacheMissCount() {
        return cacheMissCount;
    }

}

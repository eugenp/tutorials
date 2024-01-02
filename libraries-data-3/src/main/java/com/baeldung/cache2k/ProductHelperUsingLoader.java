package com.baeldung.cache2k;

import java.util.concurrent.TimeUnit;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductHelperUsingLoader {

    final Logger LOGGER = LoggerFactory.getLogger(ProductHelperUsingLoader.class);

    private Cache<String, Integer> cachedDiscounts;

    private int cacheMissCount = 0;

    public ProductHelperUsingLoader() {
        cachedDiscounts = Cache2kBuilder.of(String.class, Integer.class)
            .name("discount-loader")
            .expireAfterWrite(10, TimeUnit.MILLISECONDS)
            .entryCapacity(100)
            .loader((key) -> {
                cacheMissCount++;
                return "Sports".equalsIgnoreCase(key) ? 20 : 10;
            })
            .build();
    }

    public Integer getDiscount(String productType) {
        return cachedDiscounts.get(productType);
    }

    public int getCacheMissCount() {
        return cacheMissCount;
    }

}

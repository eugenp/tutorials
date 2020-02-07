package com.baeldung.cache2k;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.cache2k.CacheEntry;
import org.cache2k.event.CacheEntryCreatedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductHelperWithEventListener {

    final Logger LOGGER = LoggerFactory.getLogger(ProductHelperWithEventListener.class);

    private Cache<String, Integer> cachedDiscounts;

    public ProductHelperWithEventListener() {
        cachedDiscounts = Cache2kBuilder.of(String.class, Integer.class)
            .name("discount-listener")
            .eternal(false)
            .expireAfterWrite(10, TimeUnit.MILLISECONDS)
            .entryCapacity(100)
            .loader((key) -> {
                LOGGER.info("Calculating discount for {}.", key);
                return "Sports".equalsIgnoreCase(key) ? 20 : 10;
            })
            .addListener(new CacheEntryCreatedListener<String, Integer>() {
                @Override
                public void onEntryCreated(Cache<String, Integer> cache, CacheEntry<String, Integer> entry) {
                    LOGGER.info("Entry created: [{}, {}].", entry.getKey(), entry.getValue());
                }
            })
            .build();
    }

    public Integer getDiscount(String productType) {
        Integer discount = cachedDiscounts.get(productType);
        if (Objects.isNull(discount)) {
            LOGGER.info("Discount for {} not found.", productType);
            discount = 0;
        } else {
            LOGGER.info("Discount for {} found.", productType);
        }
        return discount;
    }

}

package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.cache.Cache;
import com.baeldung.architecture.hexagonal.cache.InMemoryCache;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Calculator {
    private static final String additionCacheKeyFormat = "%s_add_%s";
    private static final String divisionCacheKeyFormat = "%s_div_%s";

    private final Cache cache;

    public Calculator() {
        cache = new InMemoryCache();
    }

    public Double add(Double a, Double b) {
        Optional<String> cachedResult = cache.get(String.format(additionCacheKeyFormat, a, b));

        if(cachedResult.isPresent())
            return Double.parseDouble(cachedResult.get());

        Double result = a + b;

        cache.put(String.format(additionCacheKeyFormat, a, b), result.toString());

        return result;
    }

    public Double divide(Double a, Double b) {
        if(b == 0)
            return 0d;

        Optional<String> cachedResult = cache.get(String.format(divisionCacheKeyFormat, a, b));

        if(cachedResult.isPresent())
            return Double.parseDouble(cachedResult.get());

        Double result = a / b;

        cache.put(String.format(divisionCacheKeyFormat, a, b), result.toString());

        return result;
    }
}

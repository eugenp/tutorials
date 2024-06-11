package com.baeldung.caching.example;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = { "addresses" })
public class CustomerServiceWithParent extends AbstractService {
    //
}

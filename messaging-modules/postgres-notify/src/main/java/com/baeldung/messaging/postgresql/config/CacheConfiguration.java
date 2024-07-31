package com.baeldung.messaging.postgresql.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ConcurrentLruCache;

import com.baeldung.messaging.postgresql.domain.Order;

@Configuration
public class CacheConfiguration {
    
    @Bean
    Cache ordersCache(CacheManager cm) {
        return cm.getCache("orders");
    }
    
    @Bean
    @ConditionalOnMissingBean
    CacheManager defaultCacheManager() {
        SimpleCacheManager cm = new SimpleCacheManager();
        Cache cache = new ConcurrentMapCache("orders",false);
        cm.setCaches(Arrays.asList(cache));
        
        return cm;
    }
}

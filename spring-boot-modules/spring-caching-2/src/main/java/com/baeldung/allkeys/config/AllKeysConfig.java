package com.baeldung.allkeys.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.allkeys.service.SlowServiceWithCache;

@Configuration
@EnableCaching
public class AllKeysConfig {

    @Bean
    SlowServiceWithCache slowServiceWithCache() {
        return new SlowServiceWithCache();
    }

    @Bean
    CacheManager cacheManager() {
        return new CaffeineCacheManager();
    }

}

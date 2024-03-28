package com.baeldung.caching.disable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(@Value("${appconfig.cache.enabled}") String isCacheEnabled) {
        if (isCacheEnabled.equalsIgnoreCase("false")) {
            return new NoOpCacheManager();
        }

        return new ConcurrentMapCacheManager();
    }
}

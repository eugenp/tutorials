package com.baeldung.jetcache.spring;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBootApplication
public class SpringCacheUnitTest {
    @Configuration
    public static class SpringCacheConfig {
        @Bean
        public Cache<Integer, String> testCache(CacheManager cacheManager) {
            QuickConfig quickConfig = QuickConfig.newBuilder("testing")
                .cacheType(CacheType.LOCAL)
                .expire(Duration.ofSeconds(100))
                .build();
            return cacheManager.getOrCreateCache(quickConfig);
        }
    }

    @Autowired
    @Qualifier("testCache")
    private Cache<Integer, String> testCache;

    @Test
    void testSimpleCache() {
        testCache.put(1, "Hello");

        assertEquals("Hello", testCache.get(1));
    }
}

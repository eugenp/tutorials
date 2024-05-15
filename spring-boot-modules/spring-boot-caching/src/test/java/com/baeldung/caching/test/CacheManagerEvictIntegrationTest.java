package com.baeldung.caching.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.caching.eviction.service.CachingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CacheManagerEvictIntegrationTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        public CachingService cachingService() {
            return new CachingService();
        }

        @Bean
        public CacheManager cacheManager() {
            SimpleCacheManager cacheManager = new SimpleCacheManager();
            List<Cache> caches = new ArrayList<>();
            caches.add(cacheBeanFirst().getObject());
            caches.add(cacheBeanSecond().getObject());
            cacheManager.setCaches(caches);
            return cacheManager;
        }

        @Bean
        public ConcurrentMapCacheFactoryBean cacheBeanFirst() {
            ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
            cacheFactoryBean.setName("first");
            return cacheFactoryBean;
        }

        @Bean
        public ConcurrentMapCacheFactoryBean cacheBeanSecond() {
            ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
            cacheFactoryBean.setName("second");
            return cacheFactoryBean;
        }
    }

    @Autowired
    CachingService cachingService;

    @Before
    public void before() {
        cachingService.putToCache("first", "key1", "Baeldung");
        cachingService.putToCache("first", "key2", "Article");
        cachingService.putToCache("second", "key", "Article");
    }

    @Test
    public void givenFirstCache_whenSingleCacheValueEvictRequested_thenEmptyCacheValue() {
        cachingService.evictSingleCacheValue("first", "key1");
        String key1 = cachingService.getFromCache("first", "key1");
        assertThat(key1, is(nullValue()));
    }

    @Test
    public void givenFirstCache_whenAllCacheValueEvictRequested_thenEmptyCache() {
        cachingService.evictAllCacheValues("first");
        String key1 = cachingService.getFromCache("first", "key1");
        String key2 = cachingService.getFromCache("first", "key2");
        assertThat(key1, is(nullValue()));
        assertThat(key2, is(nullValue()));
    }

    @Test
    public void givenAllCaches_whenAllCacheEvictRequested_thenEmptyAllCaches() {
        cachingService.evictAllCaches();
        String key1 = cachingService.getFromCache("first", "key1");
        assertThat(key1, is(nullValue()));

        String key = cachingService.getFromCache("second", "key");
        assertThat(key, is(nullValue()));
    }
}

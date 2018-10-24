package org.baeldung.caching.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.baeldung.caching.eviction.CacheEvictionMain;
import org.baeldung.caching.eviction.service.CachingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CacheEvictionMain.class, CachingService.class })
public class CacheManagerEvictIntegrationTest {

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

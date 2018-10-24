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
public class CacheEvictAnnotationIntegrationTest {

    @Autowired
    CachingService cachingService;

    @Before
    public void before() {
        cachingService.putToCache("first", "key1", "Baeldung");
        cachingService.putToCache("first", "key2", "Article");
    }

    @Test
    public void givenFirstCache_whenSingleCacheValueEvictRequested_thenEmptyCacheValue() {
        cachingService.evictSingleCacheValue("key1");
        String key1 = cachingService.getFromCache("first", "key1");
        assertThat(key1, is(nullValue()));
    }

    @Test
    public void givenFirstCache_whenAllCacheValueEvictRequested_thenEmptyCache() {
        cachingService.evictAllCacheValues();
        String key1 = cachingService.getFromCache("first", "key1");
        String key2 = cachingService.getFromCache("first", "key2");
        assertThat(key1, is(nullValue()));
        assertThat(key2, is(nullValue()));
    }
}

package com.baeldung.caching.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleCacheCustomizerIntegrationTest {

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void givenCacheManagerCustomizerWhenBootstrappedThenCacheManagerCustomized() {
        assertThat(cacheManager.getCacheNames())
          .containsOnly(SimpleCacheCustomizer.USERS_CACHE, SimpleCacheCustomizer.TRANSACTIONS_CACHE);
    }
}
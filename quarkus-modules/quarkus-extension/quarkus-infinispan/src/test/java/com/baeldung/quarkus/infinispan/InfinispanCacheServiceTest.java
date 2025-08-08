package com.baeldung.quarkus.infinispan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
class InfinispanCacheServiceTest {

    @Inject
    InfinispanCacheService cacheService;

    @CacheName(InfinispanCacheService.ANOTHER_CACHE)
    @Inject
    Cache anotherCache;
    
    @BeforeEach
    void clearCache() {
        cacheService.clear();
    }

    
    @Test
    void givenNewCache_whenPutEntries_thenTheyAreStored() {
        // Given
        for (int i = 0; i < 10; i++) {
            cacheService.put("key" + i, "value" + i);
        }

        // Then
        assertEquals(10, cacheService.size());
        assertEquals("value5", cacheService.get("key5"));
    }

    @Test
    void givenEntryWithTTL_whenWaitForTTLToExpire_thenEntryIsExpired() throws InterruptedException {
        // Given
        cacheService.put("expireKey", "expireValue");

        // When
        Thread.sleep(1000); // Wait past the 600-ms TTL

        // Then
        assertNull(cacheService.get("expireKey"));
    }

    @Test
    void givenMaxEntryLimit_whenInsertMoreThanLimit_thenEvictionOccurs() {
        // Given
        Map<String, String> bulkEntries = new HashMap<>();
        for (int i = 0; i < 200; i++) {
            bulkEntries.put("evictKey" + i, "value" + i);
        }

        // When
        cacheService.bulkPut(bulkEntries);
        // Then
        assertTrue(cacheService.size() <= 10);
    }

    @Test
    void givenCacheConfig_whenChecked_thenPassivationIsEnabled() {
        assertTrue(cacheService.isPassivationEnabled(), "Passivation should be enabled");
    }
    
    @Test
    void givenCacheAnnotation_whenInstanceChecked_thenItIsInfinispanCache() {
		assertTrue(anotherCache instanceof io.quarkus.cache.infinispan.runtime.InfinispanCacheImpl);
	}
    
    @Test
    void givenCache_whenQuarkusAnnotatedMethodCalled_thenTheyAreStoredInCache() {
    	// Given
		for (int i = 0; i < 10; i++) {
			cacheService.getValueFromCache("storedKey" + i);
		}
		
		String storedValue5 = (String) anotherCache.get("storedKey5", null).await().indefinitely();
		// Then
		assertEquals("storedKey5Value",storedValue5);
	}
}

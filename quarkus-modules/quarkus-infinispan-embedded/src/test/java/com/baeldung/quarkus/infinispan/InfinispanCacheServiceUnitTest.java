package com.baeldung.quarkus.infinispan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkiverse.infinispan.embedded.runtime.cache.InfinispanCacheImpl;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
class InfinispanCacheServiceUnitTest {

    @Inject
    InfinispanCacheService cacheService;
    
    @Inject
    InfinispanAnnotatedCacheService annotatedCacheService;

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
		assertTrue(annotatedCacheService.getQuarkusCache() instanceof InfinispanCacheImpl);
	}

    @Test
    void givenEmbeddedCache_whenInstanceChecked_thenItIsInfinispanCache() {
    	assertTrue(annotatedCacheService.getEmbeddedCache() instanceof org.infinispan.Cache);
    }

    @Test
    void givenCache_whenQuarkusAnnotatedMethodCalled_thenTheyAreStoredInCache() {
    	// Given
		for (int i = 0; i < 10; i++) {
			annotatedCacheService.getValueFromCache("storedKey" + i);
		}
		
		String storedValue5 = (String)annotatedCacheService.getQuarkusCache().get("storedKey5", null).await().indefinitely();
		// Then
		assertEquals("storedKey5Value",storedValue5);
		
		String embeddedValue9 = annotatedCacheService.getEmbeddedCache().get("storedKey9");
		// Then
		assertEquals("storedKey9Value",embeddedValue9);		
	}
    
    @Test
    void givenCache_whenInvalidated_thenValueIsCleared() {
		for (int i = 0; i < 10; i++) {
			annotatedCacheService.getValueFromCache("storedKey" + i);
		}
		
		annotatedCacheService.clear("storedKey5");
		String storedValue5 = annotatedCacheService.getEmbeddedCache().get("storedKey5");
		// Then
		assertNull(storedValue5);
		
		annotatedCacheService.clearAll();
		String embeddedValue9 = annotatedCacheService.getEmbeddedCache().get("storedKey9");
		// Then
		assertEquals(annotatedCacheService.getEmbeddedCache().size(), 0);
		assertNull(embeddedValue9);		
		
    }
    
    
}

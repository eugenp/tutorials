package com.baeldung.quarkus.infinispan;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Inject;

import org.junit.jupiter.api.*;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class InfinispanCacheServiceTest {

    @Inject
    InfinispanCacheService cacheService;

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
        Thread.sleep(65 * 1000); // Wait past the 60-second TTL

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
        assertTrue(cacheService.size() <= 100);
    }

    @Test
    void givenCacheConfig_whenChecked_thenPassivationIsEnabled() {
        assertTrue(cacheService.isPassivationEnabled(), "Passivation should be enabled");
    }
}

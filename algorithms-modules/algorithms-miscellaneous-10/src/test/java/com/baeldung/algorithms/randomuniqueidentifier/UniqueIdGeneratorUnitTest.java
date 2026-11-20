package com.baeldung.algorithms.randomuniqueidentifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class UniqueIdGeneratorUnitTest {

    private UniqueIdGenerator generator;
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    @BeforeEach
    void setUp() {
        generator = new UniqueIdGenerator();
    }

    @Test
    void givenDefaultSettings_whenGenerateUniqueIdIsCalled_thenReturnsValidAlphanumericString() {
        Set<String> existingIds = new HashSet<>();
        
        String id = generator.generateUniqueId(existingIds);
        
        assertNotNull(id);
        assertEquals(8, id.length());
        assertTrue(ALPHANUMERIC_PATTERN.matcher(id).matches());
    }

    @Test
    void givenCustomLength_whenSetIdLengthIsCalled_thenGeneratorRespectsNewLength() {
        generator.setIdLength(5);
        Set<String> existingIds = new HashSet<>();

        String id = generator.generateUniqueId(existingIds);

        assertEquals(5, id.length());
    }

    @Test
    void givenExistingId_whenGenerateUniqueIdIsCalled_thenReturnsNonCollidingId() {
        // GIVEN: A set that already contains a specific ID
        Set<String> existingIds = new HashSet<>();
        String existingId = "ABC12345";
        existingIds.add(existingId);
        
        // WHEN: We generate a new ID
        String newId = generator.generateUniqueId(existingIds);
        
        // THEN: The new ID must not match the existing one
        assertNotEquals(existingId, newId);
    }

    @Test
    void givenLargeNumberRequests_whenGeneratedInBulk_thenAllIdsAreUnique() {
        Set<String> store = new HashSet<>();
        int count = 100;
        
        for (int i = 0; i < count; i++) {
            store.add(generator.generateUniqueId(store));
        }
        
        assertEquals(count, store.size(), "All 100 generated IDs should be unique");
    }

    @Test
    void givenInvalidLength_whenSetIdLengthIsCalled_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            generator.setIdLength(0);
        });
    }
}


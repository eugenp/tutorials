package com.baeldung.entityCollection;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IdExtractionUnitTest {

    @Test
    void givenListOfUsers_whenUsingClassicLoop_thenReturnListOfIds() {
        List<User> users = Arrays.asList(
                new User(1L, "A"),
                new User(2L, "B"),
                new User(3L, "C")
        );

        List<Long> ids = IdExtractor.extractIdsClassic(users);

        assertEquals(Arrays.asList(1L, 2L, 3L), ids);
    }

    @Test
    void givenEmptyUsersList_whenUsingClassicLoop_thenReturnEmptyList() {
        List<User> users = List.of();
        List<Long> ids = IdExtractor.extractIdsClassic(users);
        assertTrue(ids.isEmpty());
    }

    @Test
    void givenUsersList_whenUsingStream_thenReturnListOfIds() {
        List<User> users = Arrays.asList(
                new User(10L, "A"),
                new User(20L, "B")
        );

        List<Long> ids = IdExtractor.extractIdsStream(users);
        assertEquals(Arrays.asList(10L, 20L), ids);
    }

    @Test
    void givenEmptyUsersList_whenUsingStream_thenReturnEmptyList() {
        List<User> users = List.of();
        List<Long> ids = IdExtractor.extractIdsStream(users);
        assertTrue(ids.isEmpty());
    }

    @Test
    void givenUsersWithNullIds_whenUsingStream_thenAllowNullValuesInList() {
        List<User> users = Arrays.asList(
                new User(null, "A"),
                new User(5L, "B")
        );

        List<Long> ids = IdExtractor.extractIdsStream(users);
        assertEquals(Arrays.asList(null, 5L), ids);
    }

    @Test
    void givenUsersWithDuplicateIds_whenUsingUniqueIdExtractor_thenReturnUniqueSet() {
        List<User> users = Arrays.asList(
                new User(1L, "A"),
                new User(1L, "B"),
                new User(2L, "C")
        );

        Set<Long> ids = IdExtractor.extractUniqueIds(users);
        assertEquals(Set.of(1L, 2L), ids);
    }

    @Test
    void givenEmptyUsersList_whenUsingUniqueIdExtractor_thenReturnEmptySet() {
        List<User> users = List.of();
        Set<Long> ids = IdExtractor.extractUniqueIds(users);
        assertTrue(ids.isEmpty());
    }

    @Test
    void givenListOfUsers_whenUsingUtilityExtractor_thenReturnListOfIds() {
        List<User> users = Arrays.asList(
                new User(100L, "A"),
                new User(200L, "B")
        );

        List<Long> ids = EntityUtils.extractField(users, User::getId);
        assertEquals(Arrays.asList(100L, 200L), ids);
    }

    @Test
    void givenEmptyList_whenUsingUtilityExtractor_thenReturnEmptyList() {
        List<User> users = List.of();
        List<Long> ids = EntityUtils.extractField(users, User::getId);
        assertTrue(ids.isEmpty());
    }

    @Test
    void givenUsersWithDuplicates_whenUsingUtilityExtractorAsSet_thenReturnUniqueIds() {
        List<User> users = Arrays.asList(
                new User(5L, "A"),
                new User(5L, "B"),
                new User(6L, "C")
        );

        Set<Long> ids = EntityUtils.extractFieldAsSet(users, User::getId);
        assertEquals(Set.of(5L, 6L), ids);
    }

    @Test
    void givenEmptyList_whenUsingUtilityExtractorAsSet_thenReturnEmptySet() {
        List<User> users = List.of();
        Set<Long> ids = EntityUtils.extractFieldAsSet(users, User::getId);
        assertTrue(ids.isEmpty());
    }
}


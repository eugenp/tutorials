package com.baeldung.spring.data.jpa.querymap;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@SpringBootTest(classes = AppConfig.class, properties = {
    "spring.jpa.defer-datasource-initialization=true",
    "spring.sql.init.data-locations=classpath:user-map-query-dml.sql"
})
@DisplayName("Given: ")
class UserRepositoryIntegrationTest {

    public static final int NUMBER_OF_USERS = 50;

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("repository is present")
    void whenContextStartsRepositoryIsPresent() {
        assumeThat(repository).isNotNull();
    }

    @Test
    @DisplayName("data is loaded")
    void whenUserPersistedUserCanBeFetched() {
        final List<User> users = repository.findAll();
        assumeThat(users).hasSize(NUMBER_OF_USERS);
    }

    @Nested
    @DisplayName("When: ")
    class When {

        @Test
        @DisplayName("fetch all users in a Map with Collection all of them are present")
        void fetchUsersInMapUsingCollectionsThenAllOfThemPresent() {
            final Map<Long, User> users = repository.findAllAsMapUsingCollection();
            validateUserIdToUserMap(users);
        }

        @Test
        @DisplayName("fetch all users in a Map with Streamable all of them are present")
        void fetchUsersInMapUsingStreamableThenAllOfThemPresent() {
            final Map<Long, User> users = repository.findAllAsMapUsingStreamable();
            validateUserIdToUserMap(users);
        }

        @Test
        @DisplayName("fetch all users in a Map with Stream all of them are present")
        void fetchUsersInMapUsingStreamThenAllOfThemPresent() {
            final Map<Long, User> users = repository.findAllAsMapUsingStream();
            validateUserIdToUserMap(users);
        }

        @Test
        @DisplayName("fetch all users in a Map with Streamable wrapper all of them are present")
        void fetchUsersInMapUsingStreamableWrapperThenAllOfThemPresent() {
            final Users streamableWrapper = repository.findAllUsers();
            final Map<Long, User> users = streamableWrapper.getUserIdToUserMap();
            validateUserIdToUserMap(users);
        }

        @Test
        @DisplayName("fetch all users in a Map with Streamable wrapper "
                     + "with a filter, all of them are present")
        void fetchUsersInMapUsingStreamableWrapperWithFilterThenAllOfThemPresent() {
            final Users users = repository.findAllUsers();
            final int maxNameLength = 4;
            final List<User> actual = users.getAllUsersWithShortNames(maxNameLength);
            User[] expected = {
                new User(9L, "Moe", "Oddy"),
                new User(25L, "Lane", "Endricci"),
                new User(26L, "Doro", "Kinforth"),
                new User(34L, "Otho", "Rowan"),
                new User(39L, "Mel", "Moffet")
            };
            assertThat(actual).containsExactly(expected);
        }

        @Test
        @DisplayName("fetch all users in a Map with Streamable wrapper "
                     + "and grouping all of them are present")
        void fetchUsersInMapUsingStreamableWrapperAndGroupingThenAllOfThemPresent() {
            final Users users = repository.findAllUsers();
            final Map<Character, List<User>> alphabeticalGrouping = users.groupUsersAlphabetically();
            final List<User> actual = alphabeticalGrouping.get('A');
            User[] expected = {
                new User(2L, "Auroora", "Oats"),
                new User(4L, "Alika", "Capin"),
                new User(20L, "Artus", "Rickards"),
                new User(27L, "Antonina", "Vivian")};
            assertThat(actual).containsExactly(expected);
        }

    }

    private static void validateUserIdToUserMap(final Map<Long, User> users) {
        assertThat(users.values()).hasSize(NUMBER_OF_USERS);
        users.forEach((id, user) -> {
            assertThat(user.getId()).isEqualTo(id);
        });
    }

}
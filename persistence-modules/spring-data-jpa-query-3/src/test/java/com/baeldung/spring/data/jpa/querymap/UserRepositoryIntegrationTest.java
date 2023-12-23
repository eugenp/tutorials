package com.baeldung.spring.data.jpa.querymap;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AppConfig.class, properties = {
    "spring.jpa.defer-datasource-initialization=true",
    "spring.sql.init.data-locations=classpath:user-map-query-dml.sql"
})
class UserRepositoryIntegrationTest {

    public static final int NUMBER_OF_USERS = 50;
    @Autowired
    private UserRepository repository;

    @Test
    void whenContextStartsRepositoryIsPresent() {
        assertThat(repository).isNotNull();
    }

    @Test
    void givenCorrectDatabaseWhenUserPersistedUserCanBeFetched() {
        final List<User> users = repository.findAll();
        assertThat(users).hasSize(NUMBER_OF_USERS);
    }

    @Test
    void givenCorrectDatabaseWhenUserPersistedAllUserCanBeFetchedInMapUsingCollections() {
        final Map<Long, User> users = repository.findAllAsMapUsingCollection();
        validateUserIdToUserMap(users);
    }

    @Test
    void givenCorrectDatabaseWhenUserPersistedAllUserCanBeFetchedInMapUsingStreamable() {
        final Map<Long, User> users = repository.findAllAsMapUsingStreamable();
        validateUserIdToUserMap(users);
    }

    @Test
    void givenCorrectDatabaseWhenUserPersistedAllUserCanBeFetchedInMapUsingStream() {
        final Map<Long, User> users = repository.findAllAsMapUsingStream();
        validateUserIdToUserMap(users);
    }

    @Test
    void givenCorrectDatabaseWhenUserPersistedAllUserCanBeFetchedInCustomStreamable() {
        final Users streamableWrapper = repository.findAllUsers();
        final Map<Long, User> users = streamableWrapper.getUserIdToUserMap();
        validateUserIdToUserMap(users);

    }
    @Test
    void givenCorrectDatabaseWhenUserPersistedAllUserCanBeFetchedInCustomStreamableAndFiltered() {
        final Users streamableWrapper = repository.findAllUsers();
        final int maxNameLength = 4;
        final List<User> actual = streamableWrapper.getAllUsersWithShortNames(maxNameLength);
        User[] expected = {
            new User(9L, "Moe","Oddy"),
            new User(25L, "Lane","Endricci"),
            new User(26L, "Doro","Kinforth"),
            new User(34L, "Otho","Rowan"),
            new User(39L, "Mel","Moffet")
        };
        assertThat(actual).containsExactly(expected);
    }
    @Test
    void givenCorrectDatabaseWhenUserPersistedAllUserCanBeFetchedInCustomStreamableAndGrouped() {
        final Users streamableWrapper = repository.findAllUsers();
        final Map<Character, List<User>> alphabeticalGrouping = streamableWrapper.groupUsersAlphabetically();
        final List<User> actual = alphabeticalGrouping.get('A');
        User[] expected = {
            new User(2L, "Auroora","Oats"),
            new User(4L, "Alika","Capin"),
            new User(20L, "Artus","Rickards"),
            new User(27L, "Antonina","Vivian")};
        assertThat(actual).containsExactly(expected);
    }


    private static void validateUserIdToUserMap(final Map<Long, User> users) {
        assertThat(users.values()).hasSize(NUMBER_OF_USERS);
        users.forEach((id, user) -> {
            assertThat(user.getId()).isEqualTo(id);
        });
    }

}
package com.baeldung.listvsset;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertUpdateCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.listvsset.eager.simplelist.Application;
import com.baeldung.listvsset.eager.simplelist.Post;
import com.baeldung.listvsset.eager.simplelist.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=true",
  "logging.level.org.hibernate.SQL=debug",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneEagerSimpleListIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    private static final int ONE = 1;

    @Test
    void givenEagerListBasedUser_WhenFetchingAllUsers_ThenIssueNPlusOneRequests() {
        List<User> users = getService().findAll();
        assertSelectCount(users.size() + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerListBasedUser_WhenFetchingOneUser_ThenIssueNPlusOneRequests(Long id) {
        getService().getUserById(id);
        assertSelectCount(ONE);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerListBasedUser_whenDeletePost_ThenIssueSingleUpdate(Long id) {
        Optional<User> optionalUser = getService().getUserById(id);
        assertSelectCount(ONE);
        optionalUser.ifPresent(user -> {
            List<Post> posts = user.getPosts();
            int originalNumberOfPosts = posts.size();
            reset();
            if (!posts.isEmpty()) {
                System.out.println("\n\n\n\n\n\nRemove:");
                posts.get(0).setAuthor(null);
                getService().save(user);
                assertSelectCount(ONE);
                assertUpdateCount(ONE);
                getService().getUserById(id).ifPresent(updatedUser -> {
                    assertThat(updatedUser.getPosts()).hasSize(originalNumberOfPosts - 1);
                });
            }

        });
    }



    protected void addUsers() {
        List<User> users = jsonUtils.getUsers(User.class);
        databaseUtil.saveAll(users);
    }

}

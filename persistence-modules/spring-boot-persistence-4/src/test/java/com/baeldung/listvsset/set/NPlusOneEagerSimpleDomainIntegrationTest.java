package com.baeldung.listvsset.set;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertUpdateCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.listvsset.BaseNPlusOneIntegrationTest;
import com.baeldung.listvsset.eager.set.simpledomain.Application;
import com.baeldung.listvsset.eager.set.simpledomain.Post;
import com.baeldung.listvsset.eager.set.simpledomain.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=false",
  "logging.level.org.hibernate.SQL=info",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneEagerSimpleDomainIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    @Test
    void givenEagerSetBasedUser_WhenFetchingAllUsers_ThenIssueNPlusOneRequests() {
        List<User> users = getUserService().findAll();
        assertSelectCount(users.size() + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerSetBasedUser_WhenFetchingOneUser_ThenIssueOneRequest(Long id) {
        getUserService().getUserById(id);
        assertSelectCount(1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerListBasedUser_whenDeletePost_ThenIssueSingleUpdate(Long id) {
        Optional<User> optionalUser = getUserService().getUserById(id);
        assertSelectCount(1);
        optionalUser.ifPresent(user -> {
            Set<Post> posts = user.getPosts();
            int originalNumberOfPosts = posts.size();
            reset();
            if (!posts.isEmpty()) {
                posts.iterator().next().setAuthor(null);
                getUserService().save(user);
                assertSelectCount(1);
                assertUpdateCount(1);
                getUserService().getUserById(id).ifPresent(updatedUser -> {
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
